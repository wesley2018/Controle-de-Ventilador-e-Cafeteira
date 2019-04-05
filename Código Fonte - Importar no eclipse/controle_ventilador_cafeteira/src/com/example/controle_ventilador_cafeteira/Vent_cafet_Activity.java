package com.example.controle_ventilador_cafeteira;/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
public class Vent_cafet_Activity extends ActionBarActivity implements OnClickListener {
	
	// DECLARA��O DE VARI�VEIS
	ImageButton btVenti, btCafet, btConectar;
	TextView tvStatusVenti, tvStatusCafet;
	EditText et_Ip;
	String L, hostIp = null;
	Handler mHandler;
	long lastPress;
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO M�TODO "telaIp"
	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	// M�TODO "telaIp"
			public void telaIp(){
				setContentView(R.layout.tela_ip); // INICIALIZA A TELA
				et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
		        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOT�O
		        /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	if(btConectar.isPressed()){ // SE O BOT�O FOR PRESSIONADO
		    		onClick(btConectar); // EXECUTA A FUN��O REFERENTE AO BOT�O
		    	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/                                                                /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    }/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			// M�TODO "telaPrincipal"
			public void telaPrincipal(){  /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/ 	
				setContentView(R.layout.activity_vent_cafet); // INICIALIZA A TELA
				
				mHandler = new Handler(); // VARI�VEL "mHandler" INICIALIZADA
		        mHandler.post(mUpdate);	 // VARI�VEL "mHandler" CHAMA O M�TODO "mUpdate"
		        /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				btVenti = (ImageButton) findViewById(R.id.btVenti);  // ESTANCIA O IMAGEBUTTON
				btCafet = (ImageButton) findViewById(R.id.btCafet);
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	btVenti.setOnClickListener(this); // ATIVA O CLICK DO BOT�O
		    	btCafet.setOnClickListener(this);
		    	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	tvStatusVenti = (TextView) findViewById(R.id.tvStatusVenti);  // ESTANCIA O TEXTVIEW
		    	tvStatusCafet = (TextView) findViewById(R.id.tvStatusCafet);
		    	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				if(btVenti.isPressed()){ // SE O BOT�O FOR PRESSIONADO
					onClick(btVenti); // EXECUTA A FUN��O REFERENTE AO BOT�O
				}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				if(btCafet.isPressed()){ // SE O BOT�O FOR PRESSIONADO
					onClick(btCafet); // EXECUTA A FUN��O REFERENTE AO BOT�O
				}
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			// M�TODO QUE EXECUTA A ATUALIZA��O DO TEXTVIEW COM INFORMA��O RECEBIDAS DO ARDUINO
			private Runnable mUpdate = new Runnable() {
		    	public void run() {
		    		arduinoStatus("http://"+hostIp+"/"); // CHAMA O M�TODO "arduinoStatus"
		    		mHandler.postDelayed(this, 500); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMA��O (500 MILISEGUNDOS)
		    	}
		    };/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    public void arduinoStatus(String urlArduino){
		    	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				String urlHost = urlArduino;  // CRIA UMA STRING
				String respostaRetornada = null;  // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
				
				try{
					respostaRetornada = ConectHttpClient.executaHttpGet(urlHost);  // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
					String resposta = respostaRetornada.toString();  // STRING "resposta"
					resposta = resposta.replaceAll("\\s+", "");
					/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
					String[] b = resposta.split(","); // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")" 	     

					if(b[0].equals("VENTILIG")){ // SE POSI��O 0 DO VETOR IGUAL A "VENTILIG"				
						tvStatusVenti.setText("LIGADO"); // TEXTVIEW RECEBE LIGADO
					}
					else{/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
						if(b[0].equals("VENTIDESLIG")){ // SE POSI��O 0 DO VETOR IGUAL A "VENTIDESLIG"	
							tvStatusVenti.setText("DESLIGADO"); // TEXTVIEW RECEBE DESLIGADO					
						}
					}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
					if(b[1].equals("CAFETLIG")){ // SE POSI��O 0 DO VETOR IGUAL A "CAFETLIG"				
						tvStatusCafet.setText("LIGADA");  // TEXTVIEW RECEBE LIGADA				
					}
					else{/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
						if(b[1].equals("CAFETDESLIG")){ // SE POSI��O 0 DO VETOR IGUAL A "CAFETDESLIG"
							tvStatusCafet.setText("DESLIGADA");	// TEXTVIEW RECEBE DESLIGADA					
						}
					}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				}
				catch(Exception erro){
				}
			}
	@Override/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	public void onClick(View bt) { // M�TODO QUE GERENCIA OS CLICK'S NOS BOT�ES
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		if(bt == btConectar){ // SE BOT�O CLICKADO
			if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
				Toast.makeText(getApplicationContext(), // FUN��O TOAST
				"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
			}else{ // SEN�O
			hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
			// FUN��O QUE OCULTA O TECLADO AP�S CLICAR EM CONECTAR
			InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
		    Context.INPUT_METHOD_SERVICE);
		    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
			telaPrincipal(); // FAZ A CHAMADA DO M�TODO "telaPrincipal"
			}	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		}
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		String url = null; // CRIA UMA STRING CHAMADA "url" QUE POSSUI VALOR NULO
		
		if(bt == btVenti){ // SE BOT�O CLICKADO
			url = "http://"+hostIp+"/?B=VENTI"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
		}
		if(bt == btCafet){ // SE BOT�O CLICKADO
			url = "http://"+hostIp+"/?B=CAFET"; // STRING "url" RECEBE O VALOR AP�S O SINAL DE "="
		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		
		String urlGetHost = url; // CRIA UMA STRING CHAMADA "urlGetHost" QUE RECEBE O VALOR DA STRING "url"
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		//INICIO DO TRY CATCH
		try{/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			ConectHttpClient.executaHttpGet(urlGetHost); // PASSA O PAR�METRO PARA O O M�TODO "executaHttpGet" NA CLASSE "ConectHttpClient" E ENVIA AO ARDUINO
		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		catch(Exception erro){ // FUN��O DE EXIBI��O DO ERRO
		} // FIM DO TRY CATCH	
	}
	// M�TODO QUE VERIFICA O BOT�O DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICA��O SE PRESSIONADO 2 VEZES SEGUIDAS
    public void onBackPressed() {	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/	
	    long currentTime = System.currentTimeMillis();
	    if(currentTime - lastPress > 5000){
	        Toast.makeText(getBaseContext(), "Pressione novamente para sair.", Toast.LENGTH_LONG).show();
	        lastPress = currentTime;  
	    }else{/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	        super.onBackPressed();
	        android.os.Process.killProcess(android.os.Process.myPid());
	    }/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
