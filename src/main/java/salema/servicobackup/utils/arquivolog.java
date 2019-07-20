package salema.servicobackup.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class arquivolog {
	public String path = "";

	
	public boolean gravar(String texto) throws IOException {
		Calendar c = Calendar.getInstance();
		try {
			FileWriter logarq = new FileWriter(this.path, true);
			PrintWriter gravarArq = new PrintWriter(logarq, true);
			
			
			gravarArq.printf("%s : %s ", c.getTime(),texto);
			return true;
			
		}catch(Exception ex) {
			return false;
		}
	}
}
