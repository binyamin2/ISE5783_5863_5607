package Employee;

public class BasePlusCommissionEmployee extends CommissionEmployee{
    private float baseSalary;

    public BasePlusCommissionEmployee(String firstName, String lastName, int id, float grossSales, int commission, float baseSalary) {
        super(firstName, lastName, id, grossSales, commission);
        this.baseSalary=baseSalary;
    }

    public BasePlusCommissionEmployee() {
        super();
        this.baseSalary=baseSalary;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Base salary must be non-negative");
        }
        this.baseSalary = baseSalary;
    }


    @Override
    public String toString() {
        return "BasePlusCommissionEmployee{" +super.toString()+
                "baseSalary=" + baseSalary +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BasePlusCommissionEmployee)) {
            return false;
        }
        BasePlusCommissionEmployee other = (BasePlusCommissionEmployee) obj;
        return super.equals(obj) && baseSalary == other.baseSalary;
    }

    @Override
    public float earnings() {
        return super.earnings() + baseSalary;
    }
}
