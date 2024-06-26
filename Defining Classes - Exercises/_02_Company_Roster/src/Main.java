import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
         Map<String, List<Double>> departmentsAverageSalary = new LinkedHashMap<>();
         Map<String, List<Employee>> employees = new LinkedHashMap<>();
         String departmentMaxAverageSalary = "";
         int n = Integer.parseInt(scanner.nextLine());
         for(int i = 0; i < n; i++)
         {
             String[] items = scanner.nextLine().split(" ");
             departmentsAverageSalary = addNameSalaryOrUpdateSalaryList(departmentsAverageSalary, items);
             employees = createAndAddEmployee(employees, items);
         }
         departmentMaxAverageSalary = findHighestAverageSalaryDepartment(departmentsAverageSalary);
         printFinalState(employees, departmentMaxAverageSalary);
    }
    public static Map<String, List<Double>> addNameSalaryOrUpdateSalaryList(Map<String, List<Double>> departmentsAverageSalary,
                                                                             String[] items)
    {
        double salary = Double.parseDouble(items[1]);
        String department = items[3];
        if(departmentsAverageSalary.isEmpty() || !departmentsAverageSalary.containsKey(department))
        {
            List<Double> temp = new ArrayList<>();
            temp.add(salary);
            departmentsAverageSalary.put(department, new ArrayList<>());
            departmentsAverageSalary.put(department, temp);
        }
        else
        {
            List<Double> temp = departmentsAverageSalary.get(department);
            temp.add(salary);
        }
        return departmentsAverageSalary;
    }
    public static   Map<String, List<Employee>> createAndAddEmployee( Map<String, List<Employee>> employees, String[] items)
    {
        //name, salary, position, department, email, and age.
        String name = items[0];
        double salary = Double.parseDouble(items[1]);
        String position = items[2];
        String department = items[3];
        String email = "n/a";
        int age = -1;

        if(items.length == 5)
        {
            if(items[4].contains("@"))
            {
                email = items[4];
            }
            else
            {
                email = "n/a";
                age = Integer.parseInt(items[4]);
            }
        }
        else if(items.length == 6)
        {
            email = items[4];
            age = Integer.parseInt(items[5]);
        }
        Employee employee = new Employee(name, salary, position, department, email, age);
        if(employees.isEmpty() || !employees.containsKey(department))
        {
            employees.put(department, new ArrayList<>());
            List<Employee> currentList = new ArrayList<>();
            currentList.add(employee);
            employees.put(department, currentList);
        }
        else
        {
            List<Employee> currentList = employees.get(department);
            currentList.add(employee);
        }
        return employees;
    }
    public static String findHighestAverageSalaryDepartment(Map<String, List<Double>> departmentsAverageSalary)
    {
        double maxAverage = -Double.MAX_VALUE;
        String maxDepartmentAvSalary = "";
        for(String key : departmentsAverageSalary.keySet())
        {
            List<Double> temp = departmentsAverageSalary.get(key);
            double currentAverage = temp.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            if(currentAverage > maxAverage)
            {
                maxAverage = currentAverage;
                maxDepartmentAvSalary = key;
            }
        }
        return maxDepartmentAvSalary;
    }
    public static void printFinalState( Map<String, List<Employee>> employees, String departmentMaxAverageSalary)
    {
        System.out.println("Highest Average Salary: " + departmentMaxAverageSalary);
        for(String key : employees.keySet())
        {
            if(key.equals(departmentMaxAverageSalary))
            {
                List<Employee> current = new ArrayList<>();
                current = employees.get(key);
                current.sort(Comparator.comparing(Employee::getSalary).reversed());
                for(Employee employee : current)
                {
                    System.out.printf("%s %.2f %s %d%n",
                            employee.getName(),
                            employee.getSalary(),
                            employee.getEmail(),
                            employee.getAge());
                }
                break;

            }
        }
    }
}