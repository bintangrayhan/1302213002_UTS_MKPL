package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private boolean gender; //true = Laki-laki, false = Perempuan
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	private static final int GRADE_1_SALARY = 3000000;
	private static final int GRADE_2_SALARY = 5000000;
	private static final int GRADE_3_SALARY = 7000000;
	private static final double FOREIGNER_BONUS = 1.5;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {
		switch (grade) {
			case 1:
				monthlySalary = GRADE_1_SALARY;
				break;
			case 2:
				monthlySalary = GRADE_2_SALARY;
				break;
			case 3:
				monthlySalary = GRADE_3_SALARY;
				break;
			default:
				throw new IllegalArgumentException("Invalid grade: " + grade);
		}

		if (isForeigner) {
			monthlySalary *= FOREIGNER_BONUS; // Bonus hanya diterapkan sekali setelah gaji di-set.
		}
	}
	
	public void setAnnualDeductible(int deductible) {
		if (deductible < 0) {
			throw new IllegalArgumentException("Deductible cannot be negative");
		}
		this.annualDeductible = deductible;
	}

	public void setAdditionalIncome(int income) {
		if (income < 0) {
			throw new IllegalArgumentException("Income cannot be negative");
		}
		this.otherMonthlyIncome = income;
	}
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber; // Menggunakan parameter yang diterima
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		LocalDate currentDate = LocalDate.now();
		LocalDate joinedDate = LocalDate.of(yearJoined, monthJoined, dayJoined);

		int monthsWorkingThisYear = calculateMonthsBetween(joinedDate, currentDate);

		return TaxFunction.calculateTax(
				monthlySalary,
				otherMonthlyIncome,
				monthsWorkingThisYear,
				annualDeductible,
				spouseIdNumber.equals(""),
				childIdNumbers.size());
	}

	private int calculateMonthsBetween(LocalDate start, LocalDate end) {
		if (start.getYear() == end.getYear()) {
			return end.getMonthValue() - start.getMonthValue();
		} else {
			int monthsFromStart = 12 - start.getMonthValue();
			int monthsFromEnd = end.getMonthValue();
			return monthsFromStart + monthsFromEnd;
		}
	}

}
