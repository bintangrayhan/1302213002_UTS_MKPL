package lib;

public class TaxFunction {

	
	private static final int NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIED_ADDITIONAL = 4500000;
	private static final int CHILD_ADDITIONAL = 1500000;
	
	
	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
		
		
		
		if (numberOfMonthWorking > 12) {
            throw new IllegalArgumentException("More than 12 month working per year");
        }
		
        numberOfChildren = Math.min(numberOfChildren, 3);

        int nonTaxableIncome = NON_TAXABLE_INCOME + (isMarried ? MARRIED_ADDITIONAL : 0) + (numberOfChildren * CHILD_ADDITIONAL);

        int taxableIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking - deductible - nonTaxableIncome;
        int tax = (int) Math.round(0.05 * taxableIncome);

        return Math.max(tax, 0);
			 
	}
	
}
