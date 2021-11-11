import java.io.*;
import java.util.*;

class ledger {
	public static void main(String[] args) throws IOException {
		int choice;
		String exitans;
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("----------------------------------------------");
			System.out.println("FILE STRUCTURES LAB WITH MINI PROJECT(18ISL67)");
			System.out.println("----------------------------------------------");
			System.out.println("WELCOME TO GENERAL LEDGER PROGRAM");
			System.out.println("----------------------------------------------");
			System.out.println("1. View Journal");
			System.out.println("2. View Ledger");
			System.out.println("3. Add Record to journal");
			System.out.println("4. Post Journal to Ledger");
			System.out.println("5. Get Ledger Printout");
			System.out.println("6. Exit");
			System.out.println("----------------------------------------------");
			System.out.println("ENTER YOUR CHOICE");
			System.out.println("----------------------------------------------");
			choice = scan.nextInt();
			switch(choice) {
				case 1 :	System.out.println("View Journal Part");
							display_journal();
							break;
				case 2 :	System.out.println("View Ledger Part");
							display_ledger();
							break;
				case 3 :	System.out.println("Add record to Journal Part");
							add_record();
							break;
				case 4 :	post();
							System.out.println("Journal Posted to Ledger!");
							break;
				case 5 :	System.out.println("--------------------------------------------------------------------------");
							System.out.println("\t\t\t\tLEDGER PRINTOUT");
							print_ledger();
							break;
				case 6 :	System.exit(0);
			}
		}
	}
	//Method for displaying the journal
	public static void display_journal() throws IOException {
		String acctno = "",check = "",date = "",desc = "",cred = "",s;
		System.out.println("____________________________________________________________________________________");
		System.out.println("Acct. No\tCheck. No\tDate\t\tDescription\t\tCredit/Debit");
		System.out.println("____________________________________________________________________________________");
		BufferedReader b = new BufferedReader(new FileReader("journal.txt"));
		while((s = b.readLine())!=null)
		{
			String result[] = s.split("\\|");
			acctno = result[0];
			check = result[1];
			date = result[2];
			desc = result[3];
			cred = result[4];
			System.out.println(acctno + "\t\t" + check + "\t\t" + date + "\t" + desc + "\t\t" + cred);
		}

		b.close();
		System.out.println("____________________________________________________________________________________");
	}

	//Method for displaying the ledger
	public static void display_ledger() throws IOException{
		String s,acctno = "",acctitle = "",jan = "",feb = "",mar = "",apr = "",may = "",jun = "",jul = "",aug = "",sep = "",oct = "",nov = "",dec = "";
		System.out.println("_______________________________________________________________________________________________________________________________________");
		System.out.println("Acct. No\tAccount Title\t\tJan\tFeb\tMar\tApr\tMay\tJun\tJul\tAug\tSep\tOct\tNov\tDec");
		System.out.println("_______________________________________________________________________________________________________________________________________");
		BufferedReader b = new BufferedReader(new FileReader("ledger.txt"));
		while((s = b.readLine())!=null)
		{
			String result[] = s.split("\\|");
			acctno = result[0];
			acctitle = result[1];
			jan = result[2];
			feb = result[3];
			mar = result[4];
			apr = result[5];
			may = result[6];
			jun = result[7];
			jul = result[8];
			aug = result[9];
			sep = result[10];
			oct = result[11];
			nov = result[12];
			dec = result[13];
			System.out.println(acctno + "\t\t" + acctitle + "\t\t" + jan + "\t" + feb + "\t" +  mar + "\t" + apr + "\t" + may + "\t" + jun + "\t" + jul + "\t" + aug + "\t" + sep + "\t" + oct + "\t" + nov + "\t" + dec);
		}
		b.close();
		System.out.println("______________________________________________________________________________________________________________________________________");
	}
	//Method for adding record into the journal
	public static void add_record() throws IOException,FileNotFoundException{
		String acctno,check,date,desc,cred;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the Details of the Transaction: ");
		System.out.print("Account Number: ");
		acctno = scan.nextLine();
		System.out.print("Check Number: ");
		check = scan.nextLine();
		System.out.print("Date: ");
		date = scan.nextLine();
		System.out.print("Description: ");
		desc = scan.nextLine();
		System.out.print("Credit/Debit: ");
		cred = scan.nextLine();
		pack(acctno,check,date,desc,cred);
	}

	public static void pack(String acctno,String check,String date,String desc,String cred) throws IOException,FileNotFoundException{
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("journal.txt",true)));
		String b = acctno + "|" + check + "|" + date + "|" + desc + "|" + cred + "|";
		pw.println(b);
		pw.flush();
		pw.close();
	}
	//Method for posting the journal file to the ledger file
	public static void post() throws IOException{
		//get sorted journal and ledger list and create third list to store merged text.
		sort_journal();
		String acctno = "",check = "",date = "",desc = "",cred = "",ledgerno = "",acctitle = "";
		String olds = null;
		BufferedReader br1 = new BufferedReader(new FileReader("journal.txt"));
		BufferedReader br2 = new BufferedReader(new FileReader("ledger.txt"));
		PrintWriter pw = new PrintWriter("finalledger.txt");
		//if(ledgeritem1 == journalitem2) print(item2) movetonext(item2)
		//if(ledgeritem1 > journalitem2) means no match for ledger. movetonext(journalitem2)
		//if(ledgeritem1 < journalitem2) means item1's account details are done. movetonext(ledgeritem1)
		String s = br1.readLine();
		String t = br2.readLine();
		while((s!=null)||(t!=null)) {
			String result1[] = s.split("\\|");
			String result2[] = t.split("\\|");
			acctno = result1[0];
			check = result1[1];
			date = result1[2];
			desc = result1[3];
			cred = result1[4];
			ledgerno = result2[0];
			acctitle = result2[1];
			if(ledgerno == acctno) {
				String b = acctno + "|" + acctitle + "|" + check + "|" + date + "|" + desc + "|" + cred + "|";
				pw.println(b);
				s = br1.readLine();
			}
			else if(Integer.parseInt(ledgerno) < Integer.parseInt(acctno)) {
				if((olds == ledgerno) || (olds!=null)) {
					t = br2.readLine();
				}
				else {
					String a = acctno + "|" +  acctitle + "|0|0|0|0|";
					pw.println(a);
					t = br2.readLine();
				}
			}
			else { //ledgerno > acctno
				String b = acctno + "|" + acctitle + "|" + check + "|" + date + "|" + desc + "|" + cred + "|";
				pw.println(b);
				s = br1.readLine();
			}
			if(s == null) {
					while(t!=null)  {
						t = br2.readLine();
						if(t == null) {
								break;
						}
						String result3[] = t.split("\\|");
						ledgerno = result3[0];
						acctitle = result3[1];
						String a = ledgerno + "|" + acctitle + "|0|0|0|0|";
						pw.println(a);
					}
			}
			olds = acctno;
		}
		pw.flush();
		pw.close();
		br1.close();
		br2.close();
	}
	//Method for sorting the Journal
	public static void sort_journal() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("journal.txt"));
		ArrayList<String> str = new ArrayList<>();
		String line = "";
		while((line = br.readLine())!=null){
			str.add(line);
		}
		br.close();
		Collections.sort(str);
		FileWriter writer = new FileWriter("journal.txt");
		for(String s: str){
			writer.write(s);
			writer.write("\n");
		}
		writer.close();
	}
	//Method for getting Ledger Printout
	public static void print_ledger() throws IOException {
		String acctno = "",acctitle = "",check = "",date = "",desc = "",cred = "";
		int old = 0,prevbal = 0,newbal = 0,count = 0 ;
		BufferedReader br = new BufferedReader(new FileReader("finalledger.txt"));
		String s,l;
		BufferedReader br1 = new BufferedReader(new FileReader("ledger.txt"));
		while((s=br.readLine())!=null) {
			String result[] = s.split("\\|");
			acctno = result[0];
			acctitle = result[1];
			check = result[2];
			date = result[3];
			desc = result[4];
			cred = result[5];
			if(Integer.parseInt(acctno) > old) {
				if(old!=0) {
					l = br1.readLine();
					String result1[] = l.split("\\|");
					int i = 2;
					while(i<14) {
						prevbal += Integer.parseInt(result1[i]);
						i++;
					}
					newbal = prevbal + count;
					System.out.println("\n\t\t\t\tPrev Balance : " + prevbal + "\tNew Balance : " + newbal);
					System.out.println("--------------------------------------------------------------------------");
					prevbal = 0;
					newbal = 0;
				}
				else 
					System.out.println("--------------------------------------------------------------------------");
				System.out.println(acctno + "\t" + acctitle);
				System.out.println("--------------------------------------------------------------------------");
				System.out.println("\t\t\t" + check + "\t" + date + "\t" + desc + "\t" + cred);
				count = Integer.parseInt(cred);
			}
			else {
				System.out.println("\t\t\t" + check + "\t" + date + "\t" + desc + "\t" + cred);
				count += Integer.parseInt(cred);
			}
			old = Integer.parseInt(acctno);
		}
	}
}