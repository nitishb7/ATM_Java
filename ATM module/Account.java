import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Account {

	private long number;
	public double c_balance;
	public double s_balance;
	String cardNumber;
	private ATM_TransactionLog transactionLog;

	public Account() {

		transactionLog = new ATM_TransactionLog();

	}


	public void setAccount(long n, double c_b, double s_b) {
		this.number=n;
		this.c_balance=c_b;
		this.s_balance=s_b;


	}

	public void setAccount(String fname, String lname, String dob, String address, int pin_1, String mobNum, String email) throws IOException {

		FileWriter myWriter = new FileWriter("customers_accounts.txt",true);
		this. cardNumber = ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) + ""+ ((int)(Math.random()* (10))) +
				     "" + ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) +
				     "" + ((int)(Math.random()* (10))) + "" +  ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) +
				     "" + ((int)(Math.random()* (10))) + "" +  ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) +
				     "" + ((int)(Math.random()* (10))) + "" +  ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) ;

		this.number= Long.parseLong(((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) + ""+ ((int)(Math.random()* (10))) +
				"" + ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) +
				"" + ((int)(Math.random()* (10))) + "" +  ((int)(Math.random()* (10))) + "" + ((int)(Math.random()* (10))) +
				"" + ((int)(Math.random()* (10))));

		this.c_balance=0;
		this.s_balance=0;

		myWriter.write(fname+" " +lname +"\t" + dob + "\t" + address  + "\t" + cardNumber +
				         "\t" + pin_1 + "\t" + this.c_balance + "\t" + this.s_balance + "\t" +
				          this.number + "\t" + mobNum + "\t" + email  + System.lineSeparator());
		myWriter.close();

	}

	public void updateAccount(String fname, String lname, String dob, String mobNum, String email, String addr) throws IOException {


		Scanner sc = new Scanner(new File("customers_accounts.txt"));
		StringBuffer buffer = new StringBuffer();
		while (sc.hasNextLine()) {

			String record = sc.nextLine();
			String fields[] = record.split("\t");
			String[] fullName = fields[0].split(" ");
			long _acc_num =Long.parseLong (fields[7]);
			if (this.number == _acc_num) {
				if (fname == "" || fname == fullName[0]) {
					fname = fullName[0];
				}
				if (lname == "" || lname == fullName[1]) {
					lname = fullName[1];
				}
				if (dob == "" || dob == fields[1]) {
					dob = fields[1];
				}
				if (mobNum == "" || mobNum == fields[8]) {
					mobNum = fields[8];
				}
				if (email == "" || email == fields[9]) {
					email = fields[9];
				}if (addr == "" || addr == fields[2]) {
					addr = fields[2];
				}
				buffer.append(fname + " " + lname + "\t" + dob + "\t" + addr + "\t" + fields[3] +
						"\t" +  fields[4] + "\t" +  fields[5] + "\t" + fields[6]+ "\t" + fields[7] + "\t" + mobNum + "\t" + email +
						System.lineSeparator());
			}else
				buffer.append(record+System.lineSeparator());
		}
		String fileContents = buffer.toString();
		FileWriter writer = new FileWriter("customers_accounts.txt");
		writer.append(fileContents);
		writer.flush();

	}
	public String [] getAccount(){
	   return (new String[]{this.number+"",this.cardNumber});
	}

	public boolean deposit(double amount, String acc_type) throws IOException {
		boolean b =createTransaction("deposit", amount, acc_type);
		if(b)
			return b;
		return  false;
	}

	public boolean withdraw(double amount) throws IOException {
		if (this.c_balance-amount>=0 ){
			boolean b =createTransaction("withdraw", amount, "checking");
			if(b)
				return  true;
			else
				this.c_balance=this.c_balance+amount;

		}else if(this.c_balance+s_balance-amount>=0)
			return createTransaction("withdraw", amount, "savings");

		return false;
	}

	public boolean createTransaction(String t_type, double amount, String acc_type) throws IOException {

			if(t_type=="withdraw" && acc_type=="checking") {
				transactionLog.setTransaction(amount, this.c_balance, this.c_balance - amount, this.number,0, t_type);
				this.c_balance = this.c_balance - amount;
			}
			else if (t_type=="withdraw" && acc_type == "savings"){
				transactionLog.setTransaction(amount, this.c_balance,0,this.number, this.c_balance - amount, t_type);
				this.c_balance = 0;
			}
			else if (t_type == "deposit" && acc_type == "savings")
			{
			transactionLog.setTransaction(amount, this.s_balance,this.s_balance+amount,this.number,0, t_type);
			this.s_balance = this.s_balance + amount;
		    }
			else
		    {
				transactionLog.setTransaction(amount, this.c_balance,this.c_balance+amount,this.number,0, t_type);
				this.c_balance = this.c_balance + amount;
			}

			transactionLog.updateAccountBalance(this.number, acc_type,t_type);
		return true;

	}


}