import java.io.Serializable;

public class Employee implements Serializable {
    int empId,exp;
    String empName,jobRole;
    long empPhNo;
    Employee(int empId,String empName,long empPhNo,String jobRole,int exp){
        this.empId=empId;
        this.empName=empName;
        this.empPhNo=empPhNo;
        this.jobRole=jobRole;
        this.exp=exp;
    }

    @Override
    public String toString() {
        return String.format("%-10s%-15s%-15s%-25s%-20s",empId,empName,empPhNo,jobRole,exp);
    }
}
