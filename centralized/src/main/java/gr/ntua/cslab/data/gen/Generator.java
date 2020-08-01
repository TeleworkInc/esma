package gr.ntua.cslab.data.gen;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public abstract class Generator {

	protected int datasetSize;
	protected PrintStream out = System.out;
	
	private List<LinkedList<Integer>> buffer;
	private int flushThr;
	
	public Generator() {
		buffer = new LinkedList<LinkedList<Integer>>();
	}
	
	public Generator(int count){
		setDatasetSize(count);	
	}
	
	/**
	 * datasetSize getter
	 * @return
	 */
	public int getDatasetSize() {
		return datasetSize;
	}

	/**
	 * datasetSize setter
	 * @param datasetSize
	 */
	public void setDatasetSize(int datasetSize) {
		this.datasetSize = datasetSize;
		flushThr=100;
	}

	/**
	 * Set the output file that will be created. If no file is specified, the default output will be used
	 * (stdout).
	 * @param fileName
	 */
	public void setOutputFile(String fileName){
		try {
			out = new PrintStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract LinkedList<Integer> line();
	
	/**
	 * Creates the dataset.
	 */
	public void create(){

		out.println(datasetSize);
		for(int i=0;i<datasetSize;i++){
			if(i%flushThr==0)
				flushBuffer();
			buffer.add(line());
		}

		if(!buffer.isEmpty())
			flushBuffer();
			
		out.close();
	}
	
	private void flushBuffer(){

		StringBuilder buffer = new StringBuilder();
		for(List<Integer> l:this.buffer){

			for(Integer d:l)
				buffer.append(d.toString()+" ");

			buffer.append("\n");
		}
		
		this.buffer.clear();
		out.print(buffer.toString());
	}
	
	
	public static void runStatic(Class<? extends Generator> className, String args[]){
		try {
			Generator gen = className.newInstance();
			if(args.length<1){
				System.err.println("I need size of dataset");
				System.exit(1);
			}
			
			gen.setDatasetSize(Integer.valueOf(args[0]));
			if(args.length>1)
				gen.setOutputFile(args[1]);
			gen.create();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
