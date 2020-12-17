//Bank Management simulation
// Harkeet Singh, Id_c0797510
import java.util.*;
import java.text.*;
interface SavingsAccount
{
	final double intra = 0.04,monlim = 10000,monlim2 = 200; // intra = intrest rate , monlim= moneylimit
	void deposit(double n,Date d);
	void withdraw(double n,Date d);
}

//customer class for account create , user details add
class Customer implements SavingsAccount      
{
	String username,password,name,address,phone,accounttype;
	double bal;
	ArrayList<String> transactions;
	Customer(String username,String password,String name,String address,String phone,String accounttype,double bal,Date date)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.accounttype = accounttype;
		this.bal = bal;
		transactions  =  new ArrayList<String>(5);
		addTransaction(String.format("Initial deposit - " +NumberFormat.getCurrencyInstance().format(bal)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	void update(Date date) // this is for update total bal on intrest rate
	{
		if(bal>= 10000)   // bal= balance grether then but equal to condition..
		{
			bal += intra*bal;
		}
		else
		{
			bal -= (int)(bal/100.0);
		}
		addTransaction(String.format("Account updated. balance - " +NumberFormat.getCurrencyInstance().format(bal)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	@Override
	//deposit Money in account transaction  
	public void deposit(double amount,Date date)
	{
		bal += amount;
		addTransaction(String.format(NumberFormat.getCurrencyInstance().format(amount)+" credited to your account. bal - " +NumberFormat.getCurrencyInstance().format(bal)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	@Override
	//withdraw  Money  account transaction program 
	public void withdraw(double amount,Date date)
	{
		if(amount>(bal-200))
		{
			System.out.println("Insufficient bal.");
			return;
		}
		bal -= amount;
		addTransaction(String.format(NumberFormat.getCurrencyInstance().format(amount)+" debited from your account. bal - " +NumberFormat.getCurrencyInstance().format(bal)+" as on " + "%1$tD"+" at "+"%1$tT.",date));
	}
	private void addTransaction(String message)
	{
			transactions.add(0,message);
			if(transactions.size()>5)
			{
				transactions.remove(5);
				transactions.trimToSize();
			}
	}
}
// -----end user functions

// bank class for banking system create user account, login, update monthly int.Rate and exist system
class Bank
{
	Map<String,Customer> customerMap;
	Bank()
	{
		customerMap = new HashMap <String,Customer>();
	}
	public static void main(String []args)
	{
		Scanner sc  =  new Scanner(System.in);
		Customer customer;
		String username,password;double amount;
		Bank bank  =  new Bank();
		int choice;
	outer:	while(true)
		{
		    //this is main menu 1 to 4 menu option
			System.out.println("\n-------------------");  
			System.out.println("Welcome to Our Bank)");
			System.out.println("-------------------\n");
			System.out.println("1. Create Bank account.");
			System.out.println("2. Login.");
			System.out.println("3. Update accounts.");
			System.out.println("4. Exit.");
			System.out.print("\nEnter your choice : ");
			choice = sc.nextInt(); // this is for input value for select option
			sc.nextLine();  
			switch(choice)  //switch use for select choice of case
			{
				case 1:  // this case for create user account
					System.out.print("Enter name : ");
					String name = sc.nextLine();
					System.out.print("Enter address : ");
					String address = sc.nextLine();
					System.out.print("Enter contact number : ");
					String phone = sc.nextLine();
					System.out.println("Set username : ");
					username = sc.next();
					while(bank.customerMap.containsKey(username))
					{
						System.out.println("Username already exists. Set again : ");
						username = sc.next();
					}
					System.out.println("Set a password (8 digit-high security example 12345678Hr@) :");
					password = sc.next();
					sc.nextLine();
					while(!password.matches((("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_]).{8,}"))))
					{
						System.out.println("Invalid password condition. Set again :");
						password=sc.next();
					}
					System.out.print("Enter Bank Account Type  : "); //acount type saving,current account
					String accounttype = sc.nextLine();
					System.out.print("Enter Account Opening Balance deposit : ");  // account opening balance
					while(!sc.hasNextDouble())
					{
						System.out.println("Invalid amount. Enter again :");
						sc.nextLine();
					}
					amount=sc.nextDouble();
					sc.nextLine();
					customer = new Customer(username,password,name,address,phone,accounttype,amount,new Date());
					bank.customerMap.put(username,customer);
					break;  //break use for stop statement
				
					// case 2.login user, when user login then show sub-menu for user transaction
				case 2:
					System.out.println("Enter username : ");
					username = sc.next();
					sc.nextLine();
					System.out.println("Enter password : ");
					password = sc.next();
					sc.nextLine();
					if(bank.customerMap.containsKey(username))
					{
						customer = bank.customerMap.get(username);
						if(customer.password.equals(password))
						{
							while(true)
							{                                     //sub-menu for user transaction
								System.out.println("\n-------------------");
								System.out.println("User Transaction Menu");
								System.out.println("-------------------\n");
								System.out.println("1. Display their current bal");
								System.out.println("2. Deposit money.");
								System.out.println("3. WithDraw money.");
								System.out.println("4. Transfer money.");
								System.out.println("5. Pay utility bills");
								System.out.println("6. User information.");
								System.out.println("7. Log out.");
								System.out.print("\nEnter your choice : ");
								choice = sc.nextInt();
								sc.nextLine();
								switch(choice)
								{   
								    case 1:             // user balance check
								       System.out.println("Accountholder name : "+customer.name);
								       System.out.println("Accountholder Account Type : "+customer.accounttype);
								       System.out.println("Accountholder Account bal : "+customer.bal);
								       break;
								
									case 2:           // money deposit
									       System.out.print("Enter Deposit amount : ");
									       while(!sc.hasNextDouble())
									       {
										       System.out.println("Invalid amount. Enter again :");
										       sc.nextLine();
									       }
									       amount = sc.nextDouble();
									       sc.nextLine();
	                                       customer.deposit(amount,new Date());
									       break;
									case 3:          // money withdraw
									       System.out.print("Enter Withdraw amount :\n");
									       System.out.print("with Charges fee : Rs.12\n");
									       while(!sc.hasNextDouble())
									       {
										       System.out.println("Invalid amount. Enter again :");
										       sc.nextLine();
									       }
									       amount = sc.nextDouble();
									       sc.nextLine();
	                                       customer.withdraw(amount+12,new Date());
									       break;
									case 4:            // transfer money to other payee user
									       System.out.print("Enter payee username : ");
									       username = sc.next();
									       sc.nextLine();
									       System.out.println("Enter amount : ");
									       while(!sc.hasNextDouble())
									       {
										       System.out.println("Invalid amount. Enter again :");
										       sc.nextLine();
									       }
									       amount = sc.nextDouble();
									       sc.nextLine();
									       if(amount > 300000)
									       {
										       System.out.println("Transfer limit exceeded.");
										       break;
									       }
									       if(bank.customerMap.containsKey(username))
									       {
										       Customer payee = bank.customerMap.get(username);
										       payee.deposit(amount,new Date());
										       customer.withdraw(amount,new Date());
									       }
									       else
									       {
										       System.out.println("Username not found.");
									       }
									       break;
									case 5:   //bill payment - first enter bill Department example telephone,postpaid,electric
										   System.out.print("Enter Bill Department : ");
									       username = sc.next();
									       sc.nextLine();  
									       System.out.print("Enter Bill Amount : "); //bill amount example rs.2000
									       while(!sc.hasNextDouble())
									       {
										       System.out.println("Invalid amount. try again :");
										       sc.nextLine();
									       }
									       amount = sc.nextDouble();
									       sc.nextLine();
	                                       customer.withdraw(amount,new Date());
									       break;
									    
									case 6:   // case 6. simply display user detail print (output)
									       System.out.println("Accountholder name : "+customer.name);
									       System.out.println("Accountholder address : "+customer.address);
									       System.out.println("Accountholder contact : "+customer.phone);
									       System.out.println("Accountholder Account Type : "+customer.accounttype);
									       System.out.println("Accountholder Account Balance : "+customer.bal);
									       break;
									case 7:
									       continue outer; // outer for jump to main menu ( Login,create account menu)
								        default:
									        System.out.println("Wrong choice !");
								}
							}
						}
						else
						{
							System.out.println("Wrong username/password.");
						}
					}
					else
					{
						System.out.println("Wrong username/password.");
					}
					break;
				case 3:
					System.out.println("Enter username : ");
					username = sc.next();
					if(bank.customerMap.containsKey(username))
					{
						bank.customerMap.get(username).update(new Date());
					}
					else
					{
						System.out.println("Username , not valid, please check and re-try.");
					}
					break;
				case 4:
					System.out.println("\n **************** Thanks for visit ****************"); 
					System.exit(1);
					break;
				default:
					System.out.println("Wrong choice !");
			}
		}
	}
}