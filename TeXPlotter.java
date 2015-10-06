import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

public class TeXPlotter {
	
	private PrintWriter writer;
	
	private String head;
	private String tail;
	private Vector<String> plots = new Vector<>();
	
	
	public TeXPlotter(String file_name, String caption, String x_label, String y_label, boolean centered, boolean here){
		try {
			writer = new PrintWriter(file_name,"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		head = "\\begin{figure}";
		if(here){
			head += "[h]";
		}
		head += "\n";
		if(centered){
			head += "\\centering\n";
		}
		head += "\\begin{tikzpicture}\n"
				+ "\t\\begin{axis}[\n"
				+ "\t\txlabel="+x_label+",\n"
				+ "\t\tylabel="+y_label+"]%\n";
		
		tail = "\t\\end{axis}\n"
				+ "\\end{tikzpicture}\n"
				+ (caption != "" ? "\\caption{"+caption+"}\n" : "")
				+ "\\end{figure}\n";
	}
	
	
	
	public void addPlot(int[] x_values, int[] y_values, String format) throws TeXError{
		
		if(x_values.length != y_values.length){
			throw new TeXError("cordinate lists are of unequal length");
		}
		
		String plot = "\t\t\\addplot["+format+"] coordinates {\n";
			
		for(int i = 0; i < x_values.length; i++){
			plot += "\t\t\t(" + x_values[i] + "," + y_values[i]+ ")" + "\n";
		}
		
		plot += "\t\t};\n";
		
		plots.add(plot);
	}
	
public void addPlot(double[] x_values, double[] y_values, String format) throws TeXError{
		
		if(x_values.length != y_values.length){
			throw new TeXError("cordinate lists are of unequal length");
		}
		
		String plot = "\t\t\\addplot["+format+"] coordinates {\n";
			
		for(int i = 0; i < x_values.length; i++){
			plot += "\t\t\t(" + String.format("%.12f",x_values[i]) + "," + String.format("%.12f",y_values[i]) + ")" + "\n";
		}
		
		plot += "\t\t};\n";
		
		plots.add(plot);
	}
	
	
	
	public void close(){
		writer.close();
	}
	
	public void printToFile(){
		writer.print(head);
		for (String plot : plots) {
			writer.print(plot);
		}
		writer.print(tail);	
	}
	
	
	public void printPlot(){
		System.out.print(head);
		for (String plot : plots) {
			System.out.print(plot);
		}
		System.out.print(tail);
	}
	
	@SuppressWarnings("serial")
	public class TeXError extends Exception{
		public TeXError(String m) {
			super(m);
		}
	}
	
}
