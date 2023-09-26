import java.io.IOException;
import java.text.ParseException;

public class BankServer {

	private Bank bank;
	private boolean approvalCode;
	private String approvalCode_CardNumber_AccNumber[];

	public BankServer() throws IOException, ParseException {
		bank =new Bank();
	}

	public boolean getApproval(int pin, long CardNumber, String acc_type) throws IOException, ParseException {
		setApproval(pin, CardNumber,acc_type);
		return approvalCode;
	}

	public String[] getApproval()  {
		return (new String[]{approvalCode+"",approvalCode_CardNumber_AccNumber[0],approvalCode_CardNumber_AccNumber[1]});
	}
	
	public boolean getApproval(double amount, String type, String acc_type) throws IOException, ParseException {
		setApproval(amount, type, acc_type);
		return approvalCode;
	}

	public boolean getApproval(String fname, String lname, String dob, String mobNum, String email, String addr) throws IOException, ParseException {
		setApproval(fname, lname, dob, mobNum, email, addr);
		return approvalCode;
	}
	public void setApproval(int pin, long CardNumber, String acc_type) throws IOException, ParseException {

		approvalCode= bank.manages(pin, CardNumber, acc_type);

	}


	public void setApproval(String fname, String lname, String dob, String address, int pin_1, String mobNum, String email) throws IOException, ParseException {

		approvalCode_CardNumber_AccNumber= bank.manages(fname, lname, dob, address, pin_1, mobNum, email);
		approvalCode=true;
	}
	public void setApproval(double amount, String type, String acc_type) throws IOException, ParseException {

		approvalCode= bank.manages(amount, type,acc_type);

	}

	public void setApproval(String fname, String lname, String dob, String mobNum, String email, String addr) throws IOException, ParseException {

		approvalCode= bank.manages(fname, lname, dob, mobNum, email, addr);

	}

	public void print(int pin, long CardNumber) throws IOException, ParseException {
		bank.print(pin, CardNumber);
	}


}