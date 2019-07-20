package salema.servicobackup.quartz.jobs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;


public class ValidMoveBackup implements Job{
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException{
		
		SchedulerContext schedulerContext = null;
		 try {
			schedulerContext = arg0.getScheduler().getContext();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 //Obtem caminho do arquivo de escrita em log
		String Arqlog = schedulerContext.getString("logs");
		
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>> diretorios = (ArrayList<ArrayList<String>>) schedulerContext.get("diretorios");
		for(int x =0; x < diretorios.get(0).size(); x++) {
			File diretorio1   = new File(diretorios.get(0).get(x));
			String strDiretorio1 = diretorios.get(0).get(x);
			String backup1    = diretorios.get(1).get(x);
		
			
			
			try {
				ListarMover(diretorio1, strDiretorio1, backup1, Arqlog);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
		}
		

	}
	
	@SuppressWarnings("resource")
	public void ListarMover(final File folder, String strDiretorio1,String backup1,String arqlog) throws IOException {
	    for (final File fileEntry : folder.listFiles()) {
	            String arquivo = fileEntry.getName();
	            File camarq = new File(strDiretorio1+"/"+arquivo);
	            File destino = new File(backup1);
	            Calendar c = Calendar.getInstance();
	            
	    		FileWriter logarq = new FileWriter(arqlog, true);
	    		PrintWriter gravarArq = new PrintWriter(logarq, true);
	   
	            boolean sucesso = camarq.renameTo(new File(destino, camarq.getName()));
	            if (sucesso) {
	                System.out.println(c.getTime()+": Arquivo "+arquivo+" movido para '" + destino.getAbsolutePath() + "' ");
	                gravarArq.printf("%s : Arquivo %s movido para %s \n", c.getTime(), arquivo, destino.getAbsolutePath());
	            } else {
	                System.out.println(c.getTime()+": Erro ao mover arquivo '" + camarq.getAbsolutePath() + "' para '"
	                        + destino.getAbsolutePath() + "' \n");
	                gravarArq.printf("%s : Erro ao mover arquivo %s para  %s \n", c.getTime(), camarq.getAbsolutePath(), destino.getAbsolutePath() );
	            }
	            
	            logarq.close();
	        }
	    }

}



	
	


