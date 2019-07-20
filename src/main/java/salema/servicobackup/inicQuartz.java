package salema.servicobackup;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.xml.sax.SAXException;

import salema.servicobackup.config.configuracoes;
import salema.servicobackup.quartz.jobs.ValidMoveBackup;
import salema.servicobackup.utils.arquivolog;

public class inicQuartz {

	
	String logs;
    public static void main( String[] args ) throws SchedulerException, IOException, SAXException, ParserConfigurationException
    {
    	arquivolog Arqlog = new arquivolog();
    	Arqlog.path = "/home/airtonlirajr/Área de Trabalho/logs/logapplication.txt";

        
        Arqlog.gravar("Iniciando aplicação... \n");
//        Properties properties = new Properties();
//        
//        
//        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
//        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
//        properties.put("org.quartz.jobStore.dataSource","quartzDataSource");
//        
//        
//        properties.put("org.quartz.dataSource.quartzDataSource.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        properties.put("org.quartz.dataSource.quartzDataSource.URL", "jdbc:sqlserver://localhost:1433;databaseName=QUARTZBD");
//        properties.put("org.quartz.dataSource.quartzDataSource.user", "sa");
//        properties.put("org.quartz.dataSource.quartzDataSource.password", "Vmw4r3s4l3m4@");
//        properties.put("org.quartz.threadPool.threadCount", "5");
      
        
        // Monta o job com base na classe que possui o execute de start
        ValidMoveBackup objJob = new ValidMoveBackup();
        JobDetail job1 = JobBuilder.newJob(objJob.getClass()).build();


        
        //Cria agendamento do job que será executado para sempre de 3 em 3 segundos
        SimpleScheduleBuilder agendamento = SimpleScheduleBuilder.simpleSchedule();
        agendamento.withIntervalInSeconds(03);
        agendamento.repeatForever();
        
        // Cria o disparo com base no agendamento
        Trigger disparo = TriggerBuilder.newTrigger().withIdentity("Trigger1").withSchedule(agendamento).build();
        
        
        //Cria o scheduler oficial que recebe o job e o agendamento
        Scheduler scheduleoficial = new StdSchedulerFactory().getScheduler();
        
        
        //Obtem diretorios
        configuracoes Config = new configuracoes();
        if (!Config.valido){
        	throw new IllegalArgumentException("Estrutura de configuração do config.xml esta invalido! ");
        }
        
        //Informa no contexto informações chave para leitura de repositorios.
        scheduleoficial.getContext().put("diretorios", Config.diretorios);
        scheduleoficial.getContext().put("logs", Config.logs);
        Arqlog.gravar("Scheduller do quartz será iniciado! \n");
        scheduleoficial.start();
        scheduleoficial.scheduleJob(job1, disparo);
        Arqlog.gravar("Scheduller iniciado! \n");

        
    }
}
