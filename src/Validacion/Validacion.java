package Validacion;
/**
 *
 * @author Christian
 */
public class Validacion {
     //Funciï¿½n booleana para validar cï¿½dula
	 public boolean ValidaCedula(String cedula) {
	  
	  //Declaraciï¿½n de variables a usar
	  boolean valida=false;
	  int primeros2, tercerD, Dverificador, multiplicar, suma=0, aux;
	  int []digitos=new int[9];
	  
	  //Primer try comprueba la longitud de cadena que no sea diferente de 10
	  try {
	   if(cedula.length()!=10)throw new DatosIncorrectos("La cedula debe contener 10 digitos sin espacios<--\n");
	   
	   //Segundo try comprueba que todos los dï¿½gitos sean numï¿½ricos
	   try {
	    
	    //Transformaciï¿½n de cada carï¿½cter a un byte
	    Dverificador=Integer.parseInt(""+cedula.charAt(9));
	    primeros2=Integer.parseInt(cedula.substring(0, 2));
	    tercerD=Integer.parseInt(""+cedula.charAt(2));
	    for (int i=0; i<9; i++) {
	     digitos[i]=Integer.parseInt(""+cedula.charAt(i));
	    }
	    //Verificar segundo dï¿½gito
	    if(primeros2>=1 & primeros2<=24) {
	     if(tercerD<=6) {
	      //Mï¿½dulo 10 multiplicar digitos impares por 2
	      for (int i=0; i<9; i=(int) (i+2)) {
	       multiplicar=(int) (digitos[i]*2);
	       if(multiplicar>9) {
	        multiplicar=(int) (multiplicar-9);
	       }
	       suma=(int) (suma+multiplicar);
	      }
	      //Mï¿½dulo 10 multiplicar digitos pares por 1
	      for (int i=1; i<9; i=(int) (i+2)) {
	       multiplicar=(int) (digitos[i]*1);
	       suma=(int) (suma+multiplicar);
	      }
	      //Obtener la decena superior de la suma
	      aux=suma;
	      while(aux%10!=0) {
	       aux=(int) (aux+1);
	      }
	      suma=(int) (aux-suma);
	      //Comprobar la suma con dï¿½gito verificador (ï¿½ltimo Dï¿½gito)
	      if(suma!=Dverificador)throw new DatosIncorrectos("Revise nuevamente los digitos de su cedula<--\n");
	      valida=true; 
	     }
	    }
	   }catch(NumberFormatException e) {
	    System.out.println("La cedula debe contener solo digitos nuericos <--\n");
	   }
	  }catch(DatosIncorrectos e) {
	   System.out.println(e.getMessage());
	  }
	  return valida;
	 }
}
