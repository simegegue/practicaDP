package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import domain.MonthlyBill;


@Repository
public interface MonthlyBillRepository extends JpaRepository<MonthlyBill, Integer>{
	
	// Admin dashboard -----------------------------------------------------------
	
	@Query("select 1.0*(select count(mb1)from MonthlyBill mb1 where mb1.payMoment!=null)/(select count(mb2) from MonthlyBill mb2) from MonthlyBill mb where mb.payMoment!=null")
	Double avgPaidMonthlyBills();
	
	@Query("select stddev(mb) from MonthlyBill mb where mb.payMoment!=null")
	Double stdDevPaidMonthlyBills();
	
	@Query("select 1.0*(select count(mb1)from MonthlyBill mb1 where mb1.payMoment=null and mb1.createMoment-current_date>=30)/ (select count(mb2) from MonthlyBill mb2) from MonthlyBill mb where mb.payMoment=null and mb.createMoment-current_date>=30")
	Double avgUnpaidMonthlyBills();
	
	@Query("select stddev(mb) from MonthlyBill mb where mb.payMoment=null and mb.createMoment-current_date>=30")
	Double stdDevUnpaidMonthlyBills();
	
}
