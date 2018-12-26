package io.dtchain.entity;

public class LeaveTable
{
	private String empName;
	private String dept;
	private String startDate;
	private String endDate;
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getDept()
	{
		return dept;
	}
	public void setDept(String dept)
	{
		this.dept = dept;
	}
	public String getStartDate()
	{
		return startDate;
	}
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	@Override
	public String toString()
	{
		return "LeaveTable [empName=" + empName + ", dept=" + dept + ", startData=" + startDate + ", endData=" + endDate
		        + "]";
	}
	
}
