import java.io.*;
import java.util.*;

public class EmployeeManagement {
    static Scanner sc=new Scanner(System.in);
    static File file;
    static FileOutputStream fos;
    static ObjectOutputStream oos;
    static FileInputStream fis;
    static ObjectInputStream ois;
    static HashMap<Integer,Employee> empData=new HashMap<>();
    static TreeSet<Integer> ts;
    static int index,id=1000,empId,exp;
    static String empName,jobRole;
    static long empPhNo;
    static boolean isValidData=false;
    public static void main(String[] args) {
        boolean b=true;
        try{
            file = new File("/Users/kishore/IdeaProjects/employee.management.system/src/EmpData/empData.txt");
            if(file.exists())
            {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                empData= (HashMap<Integer,Employee>) ois.readObject();
                ts=new TreeSet<>(empData.keySet());
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        do {
            System.out.println("\n***WELCOME TO EMPLOYEE MANAGEMENT SYSTEM***\n");
            System.out.println("1. View all Employees\n2. Add Employee\n3. Edit Employee Details\n4. Delete Employee Details\n5. Exit");
            System.out.println("Enter Your Choice:");

            try {
                index=sc.nextInt();
            }catch (Exception e){
                System.out.println("Enter the valid input..!");
            }

            switch (index){
                case 1:{
                    try {
                        fis=new FileInputStream(file);
                        ois=new ObjectInputStream(fis);
                        HashMap<Integer,Employee> e=(HashMap<Integer,Employee>) ois.readObject();
                        Functions.display(e);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println(e);
                    }
                    break;
                }
                case 2:{
                    addEmployee();
                    Functions.autoSave();
                    break;
                }
                case 3:{
                    editEmployee();
                    break;
                }
                case 4:{
                    removeEmployee();
                    break;
                }
                case 5:{
                    Functions.autoSave();
                    System.out.println("Exiting...!");
                    b=false;
                    break;
                }
                default:System.out.println("Please Enter Valid Input!");break;
            }
        }while (b);
    }
    static void addEmployee(){
        do {
            //Id Auto-Generated
            id=ts.last();
            System.out.println("\n----Fill the details of the employee----\nEmployee ID: "+ ++id);
            //Getting name
            do {
                System.out.println("Employee Name:");
                empName=sc.next();
                if (!checkName(empName)) {
                    break;
                } else {
                    System.out.println("Enter the valid name..!");
                }
            }while (true);
            //Getting Ph-No
            do {
                while (!isValidData){
                    try {
                        System.out.println("Employee Ph.No:");
                        empPhNo= sc.nextLong();
                        isValidData=true;
                    }catch (Exception e) {
                        System.out.println("Enter the valid number..!");
                        sc.nextLine();
                    }
                }
                isValidData=false;
            }while (Functions.checkPhNo(empPhNo));
            //Getting Job Role
            System.out.println("Employee Designation:");
            System.out.println("1. Software Development\n2. Software Testing\n3. UI/UX Designer\n4. Human Resource\n5. Business Development\nEnter your choice:");
            String[] str={"Software Development","Software Testing","UI/UX Designer","Human Resource","Business Development"};
            jobRole=str[sc.nextInt()-1];
            //Getting Experience
            do {
                while (!isValidData)
                    try {
                        System.out.println("Experience:");
                        exp=sc.nextInt();
                        isValidData=true;
                    }catch (Exception e){
                        System.out.println("Invalid input! Please enter a valid integer.");
                        sc.nextLine();
                    }
                if(exp>25)
                    System.out.println("Experience should be less than 25years..");
                else
                    break;
            }while (true);
            //Put all the data into the HashMap
            empData.put(id,(new Employee(id,empName,empPhNo,jobRole,exp)));
            Functions.display(empData);
            System.out.println("\n***Details Added Successfully***\n");
            Functions.autoSave();
            System.out.println("Add one more Employee?\n1. Yes\n2. No");
        }while (sc.nextInt()==1);
    }
    static void removeEmployee(){
        Functions.display(empData);
        while (!isValidData){
            try {
                System.out.println("\nEnter the Employee-ID to Delete the Details:");
                empId=sc.nextInt();
                isValidData=true;
                if(empData.keySet().contains(empId)){
                    int count=0;
                    for (Map.Entry<Integer, Employee> e:empData.entrySet()) {
                        if(e.getKey()==empId){
                            empData.remove(empId);
                            System.out.println("Details of the Employee is Successfully Removed...\n");
                            Functions.display(empData);
                            count++;
                            if(count==0)
                                System.out.println("\nEmployee Details are not available, Please enter a valid ID!!");
                        }
                    }
                }else System.out.println("Employee does not exist..!");
            }catch (Exception ex){
                System.out.println("Enter valid input..!");
                sc.nextLine();
            }
        }
    }
    static boolean checkName(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    static void editEmployee(){
        Functions.display(empData);
        System.out.println("\nEnter the Employee-ID to Edit the Details");
        int edit;
        boolean condition=false;
        empId=sc.nextInt();
        if(empData.keySet().contains(empId)){
            for (Employee e:empData.values()) {
                if(empId==e.empId){
                    do {
                        System.out.println("\nEdit details of "+e.empName+"\n1. Employee Name\n2. Phone No.\n3. Designation\n4. Experience\n5. Go Back\n");
                        System.out.println("Enter Your Choice:");
                        edit= sc.nextInt();
                        switch (edit){
                            case 1:{
                                do {
                                    System.out.println("\nEnter Employee New Name:");
                                    empName= sc.next();
                                    if(checkName(empName)){
                                        System.out.println("Enter the valid name..!");
                                    }else{
                                        e.empName=empName;
                                        System.out.println("Employee Name Successfully Updated.\n");
                                        break;
                                    }
                                }while (true);
                                break;
                            }
                            case 2:{
                                do {
                                    while (!isValidData){
                                        try {
                                            System.out.println("\nEnter New Phone Number:");
                                            empPhNo= sc.nextLong();
                                            isValidData=true;
                                        }catch (Exception e2){
                                            System.out.println("Enter a valid Phone Number..!");
                                            sc.nextLine();
                                        }
                                    }
                                }while (Functions.checkPhNo(empPhNo));
                                e.empPhNo=empPhNo;
                                System.out.println("Phone Number Updated Successfully.\n");
                                break;
                            }
                            case 3:{
                                while (!isValidData){
                                    try {
                                        System.out.println("\nEnter New Designation:");
                                        System.out.println("1. Software Development\n2. Software Testing\n3. UI/UX Designer\n4. Human Resource\n5. Business Development\nEnter your choice:");
                                        String[] str={"Software Development","Software Testing","UI/UX Designer","Human Resource","Business Development"};
                                        jobRole=str[sc.nextInt()-1];
                                        isValidData=true;
                                    }catch (Exception e2){
                                        System.out.println("Enter the correct option from the above list..!");
                                        sc.nextLine();
                                    }
                                }
                                e.jobRole=jobRole;
                                System.out.println("Designation Updated Successfully.\n");
                                break;
                            }
                            case 4:{
                                while (!isValidData){
                                    try{
                                        System.out.println("\nEnter the experience of the employee:");
                                        exp= sc.nextInt();
                                        isValidData=true;
                                    }catch (Exception e2){
                                        System.out.println("Enter the valid number");
                                    }
                                    if(exp>25) {
                                        System.out.println("Experience should be less than 25years..");
                                        isValidData=false;
                                    }
                                }
                                e.exp=exp;
                                System.out.println("Experience Updated Successfully.\n");
                                break;
                            }
                            case 5:{
                                condition=false;
                                break;
                            }
                            default:{
                                System.out.println("Enter correct choice from the above list..!");
                                condition=true;
                                break;
                            }
                        }
                    }while (condition);
                    Functions.autoSave();
                }
            }
        }else System.out.println("Employee does not exist..!");
    }
}
