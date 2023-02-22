package Employee;

public class HourlyEmployee extends Employee {
    private int hours;
    private float wage;

    public HourlyEmployee(String firstName, String lastName, int id, int hours, float wage) {
        super(firstName, lastName, id);
        this.hours = hours;
        this.wage = wage;
    }

    public HourlyEmployee() {
        super();
        this.hours = 0;
        this.wage = 0;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours)  {
        if (hours>=0)
            this.hours = hours;
        else
            throw new IllegalArgumentException("hours must be positive");
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        if (wage>=0)
            this.wage = wage;
        else
            throw new IllegalArgumentException("wage must be positive");
    }

    @Override
    public String toString() {
        return "HourlyEmployee{"+ super.toString()+
                "hours=" + hours +
                ", wage=" + wage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HourlyEmployee that = (HourlyEmployee) o;

        if (hours != that.hours) return false;
        return Float.compare(that.wage, wage) == 0;
    }


    @Override
    public float earnings() {
        return wage*hours;
    }
}
