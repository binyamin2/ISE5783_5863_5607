package Employee;

public class Payroll {
    public static void main(String[] args) {
        Employee[] employees = new Employee[3];

        // Initialize employees array
        employees[0] = new HourlyEmployee("John", "Doe", 123, 40, 20);
        employees[1] = new CommissionEmployee("Jane", "Doe", 456, 10000, 10);
        employees[2] = new BasePlusCommissionEmployee("Bob", "Smith", 789, 20000, 5, 1000);

        // Print details and earnings of each employee
        for (Employee emp : employees) {
            System.out.println(emp);
            System.out.printf("Earnings: $%.2f%n", emp.earnings());

            // If employee is a BasePlusCommissionEmployee, give a 10% bonus
            if (emp instanceof BasePlusCommissionEmployee) {
                BasePlusCommissionEmployee bpce = (BasePlusCommissionEmployee) emp;
                bpce.setBaseSalary((float) (bpce.getBaseSalary() * 1.1));
                System.out.printf("New base salary for %s: $%.2f%n", emp.getFirstName(), bpce.getBaseSalary());
                System.out.printf("New earnings for %s: $%.2f%n", emp.getFirstName(), emp.earnings());
            }
            System.out.println("----------------------");
        }
    }
}