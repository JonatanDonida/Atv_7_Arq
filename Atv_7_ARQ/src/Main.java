import java.util.HashMap;
import java.util.Map;

interface TaxStrategy {
    double calculateTax(double salary);
}

class DeveloperTax implements TaxStrategy {
    @Override
    public double calculateTax(double salary) {
        return (salary > 2000) ? salary * 0.15 : 0;
    }
}

class ManagerTax implements TaxStrategy {
    @Override
    public double calculateTax(double salary) {
        return (salary > 3500) ? salary * 0.20 : 0;
    }
}

class DefaultTax implements TaxStrategy {
    @Override
    public double calculateTax(double salary) {
        return 0;
    }
}

class TaxStrategyFactory {
    private static final Map<String, TaxStrategy> strategies = new HashMap<>();

    static {
        strategies.put("DESENVOLVEDOR", new DeveloperTax());
        strategies.put("GERENTE", new ManagerTax());
    }

    public static TaxStrategy getStrategy(String role) {
        return strategies.getOrDefault(role.toUpperCase(), new DefaultTax());
    }
}

class Employee {
    private String name;
    private String role;
    private double baseSalary;

    public Employee(String name, String role, double baseSalary) {
        this.name = name;
        this.role = role;
        this.baseSalary = baseSalary;
    }

    public double calculateTax() {
        TaxStrategy strategy = TaxStrategyFactory.getStrategy(role);
        return strategy.calculateTax(baseSalary);
    }

    public String getRole() { return role; }
    public double getBaseSalary() { return baseSalary; }
}

public class Main {
    public static void main(String[] args) {
        Employee dev = new Employee("Ana", "Desenvolvedor", 2500);
        Employee manager = new Employee("Carlos", "Gerente", 4000);
        Employee intern = new Employee("Pedro", "Estagiário", 1000);

        System.out.println("Imposto Dev: R$" + dev.calculateTax());       // 375.0
        System.out.println("Imposto Gerente: R$" + manager.calculateTax()); // 800.0
        System.out.println("Imposto Estagiário: R$" + intern.calculateTax()); // 0.0
    }
}