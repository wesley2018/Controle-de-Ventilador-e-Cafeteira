/*ONDE EXISTIR "//" SIGNIFICA QUE É UM COMENTÁRIO REFERENTE A LINHA*/

//INCLUSÃO DAS BIBLIOTECAS NECESSÁRIAS PARA A EXECUÇÃO DO CÓDIGO

#include <SPI.h>
#include <Client.h>
#include <Ethernet.h>
#include <Server.h>
#include <Udp.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }; // NÃO PRECISA MEXER
byte ip[] = { 192, 168, 0, 177 }; // COLOQUE UMA FAIXA DE IP DISPONÍVEL DO SEU ROTEADOR. EX: 192.168.1.110  **** ISSO VARIA, NO MEU CASO É: 192.168.0.177
byte gateway[] = { 192, 168, 0, 1 };// MUDE PARA O GATEWAY PADRÃO DO SEU ROTEADOR **** NO MEU CASO É O 192.168.0.1
byte subnet[] = { 255, 255, 255, 0 }; //NÃO PRECISA MEXER
EthernetServer server(80); //CASO OCORRA PROBLEMAS COM A PORTA 80, UTILIZE OUTRA (EX:8082,8089)
byte sampledata=50;            

#define Rele1 6 // DEFINE O PINO UTILIZADO PELO RELÉ NO CANAL 1
#define Rele2 7 // DEFINE O PINO UTILIZADO PELO RELÉ NO CANAL 2

int stVentilador = 0; // VARIÁVEL PARA GUARDAR O STATUS DO CANAL 1
int stCafeteira = 0; // VARIÁVEL PARA GUARDAR O STATUS DO CANAL 2

String readString = String(30); //CRIA UMA STRING CHAMADA "readString"

String VENTILADOR = "VENTIDESLIG,"; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING
String CAFETEIRA = "CAFETDESLIG"; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING

void setup(){

  pinMode(Rele1,OUTPUT); // DEFINE O PINO COMO SAÍDA
  pinMode(Rele2,OUTPUT); // DEFINE O PINO COMO SAÍDA
  digitalWrite(Rele1, HIGH); // INICIA O PINO COM SINAL ALTO
  digitalWrite(Rele2, HIGH); // INICIA O PINO COM SINAL ALTO

  Ethernet.begin(mac, ip, gateway, subnet); // INICIALIZA A CONEXÃO ETHERNET
}
void loop(){
EthernetClient client = server.available(); // CRIA UMA VARIÁVEL CHAMADA client
  if (client) { //SE EXISTE CLIENTE
    while (client.connected()) { // ENQUANTO  EXISTIR CLIENTE CONECTADO
   if (client.available()) { // SE EXISTIR CLIENTE HABILITADO
    char c = client.read(); // CRIA A VARIÁVEL c

    if (readString.length() < 100) // SE O ARRAY FOR MENOR QUE 100
      {
        readString += c; // "readstring" VAI RECEBER OS CARACTERES LIDO
      }
        if (c == '\n') { // SE ENCONTRAR "\n" É O FINAL DO CABEÇALHO DA REQUISIÇÃO HTTP
          if (readString.indexOf("?") <0) //SE ENCONTRAR O CARACTER "?"
          {
          }
          else // SENÃO
        if(readString.indexOf("B=VENTI") >0){ // SE ENCONTRAR O PARÂMETRO B=VENTI
        
        if (stVentilador == 0){ // SE VARIAVEL FOR IGUAL A 0 FAZ
              digitalWrite(Rele1, LOW); // SETA A O PINO EM BAIXO
              VENTILADOR = "VENTILIG,"; // VARIÁVEL RECEBE O VALOR
              stVentilador = 1; // VAIRÁVEL RECEBE O VALOR
          }else{
                if (stVentilador == 1){ // SE VARIAVEL FOR IGUAL A 1 FAZ
                      digitalWrite(Rele1, HIGH); // SETA A O PINO EM ALTO
                      VENTILADOR = "VENTIDESLIG,"; // VARIÁVEL RECEBE O VALOR
                      stVentilador = 0; // VARIÁVEL RECEBE O VALOR
                }
           }
        }
        if(readString.indexOf("B=CAFET") >0){ // SE ENCONTRAR O PARÂMETRO "B=CAFET"
        
        if (stCafeteira == 0){ // SE VARIAVEL FOR IGUAL A 0 FAZ
              digitalWrite(Rele2, LOW); // SETA A O PINO EM BAIXO
              CAFETEIRA = "CAFETLIG";// VARIÁVEL RECEBE O VALOR
              stCafeteira = 1;// VARIÁVEL RECEBE O VALOR
          }else{
                if (stCafeteira == 1){ // SE VARIAVEL FOR IGUAL A 1 FAZ
                      digitalWrite(Rele2, HIGH); // SETA A O PINO EM ALTO
                      CAFETEIRA = "CAFETDESLIG";// VARIÁVEL RECEBE O VALOR
                      stCafeteira = 0;// VARIÁVEL RECEBE O VALOR
                }
           }
        }        
          client.println("HTTP/1.1 200 OK"); // ESCREVE PARA O CLIENTE A VERSÃO DO HTTP
          client.println("Content-Type: text/html"); // ESCREVE PARA O CLIENTE O TIPO DE CONTEÚDO(texto/html)
          client.println();
          
           client.println(VENTILADOR); // RETORNA PARA O CLIENTE O STATUS DO VENTILADOR
           client.println(CAFETEIRA); // RETORNA PARA O CLIENTE O STATUS DA CAFETEIRA
           readString="";
          client.stop(); // FINALIZA A REQUISIÇÃO HTTP
            }
          }
        }
      }
 }
