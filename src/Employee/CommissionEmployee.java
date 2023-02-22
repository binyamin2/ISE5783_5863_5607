package Employee;

public class CommissionEmployee extends Employee{
    private float grossSales;
    private int commission;

    public CommissionEmployee(String firstName, String lastName, int id, float grossSales, int commission) {
        super(firstName, lastName, id);
        this.grossSales = grossSales;
        this.commission = commission;
    }

    public CommissionEmployee() {
        super();
        this.grossSales = 0;
        this.commission = 0;
    }

    public float getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(float grossSales) {
        if (grossSales < 0) {
            throw new IllegalArgumentException("Gross sales cannot be negative");
        }
        this.grossSales = grossSales;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        if (commission < 0||commission<=100) {
            throw new IllegalArgumentException("Commission must be positive smaller than 100");
        }
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "CommissionEmployee{"+super.toString() +
                "grossSales=" + grossSales +
                ", commission=" + commission +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CommissionEmployee)) {
            return false;
        }
        CommissionEmployee other = (CommissionEmployee) obj;
        return super.equals(obj) && grossSales == other.grossSales && commission == other.commission;
    }

    @Override
    public float earnings() {
        return grossSales * commission / 100;
    }
}

