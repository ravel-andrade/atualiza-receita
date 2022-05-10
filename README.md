 Este sistema tem como objetivo simular uma interação com um serviço externo para
atualizar dados bancários a partir de uma planilha csv.

 Para rodar esta aplicação devemos primeiro localizar o arquivo jar desta aplicação. Atualmente
ele se encontra na pasta target.
 Estando na mesma pasta do jar execute uma das opções abaixo em seu terminal:

 java -jar standalone-application-0.0.1-SNAPSHOT.jar {caminho do arquivo csv}
 ou
 java -jar standalone-application-0.0.1-SNAPSHOT.jar {caminho do arquivo} {destino do arquivo final}

 As colunas que devem ser utilizadas pelo csv são as seguintes:
  agencia,conta,saldo,status

  Seu formato é:
  agencia: String
  conta: String
  saldo: Double
  status: String

  Informações técnicas e dependênicas:
  Java version: 1.8
  opencsv: 5.3


