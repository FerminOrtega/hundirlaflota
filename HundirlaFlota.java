package hundirlaflota;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Fermín Ortega Domínguez
 */
public class HundirlaFlota {

    public static String[] reglas= new String[11];
    public static String[][] tablero= new String[11][11];
    public static ArrayList<String> ocupadas=new ArrayList<String>();
    public static ArrayList<String> repetidas=new ArrayList<String>();
    public static ArrayList<String> posfinal=new ArrayList<String>();
    public static String user;
    public static int posx;
    public static int posy;
    public static void main(String[] args) {
        ArrayList<barco> barcos= new ArrayList<barco>();
        Scanner teclado=new Scanner(System.in);
        int intentos=0;
        rellenarreglas();
        rellenar();
        rellenartablero();
        mostartablero();
        //GENERACION DE BARCOS
        for (int i=0;i<2;i++){
            genenarbarcos(3);
            barco barc=new barco(3,"Acorazado",posfinal.get(i));
            barcos.add(barc);
        }
        for (int i=2;i<5;i++){
            genenarbarcos(2);
            barco barc=new barco(2,"Buque",posfinal.get(i));
            barcos.add(barc);
        }
        for (int i=5;i<9;i++){
            genenarbarcos(1);
            barco barc=new barco(1,"Submarino",posfinal.get(i));
            barcos.add(barc);
        }
        genenarbarcos(4);
        barco barc=new barco(4,"Portaavion",posfinal.get(9));
        barcos.add(barc);
        //FIN DE GENERACION DE BARCOS
        
        //    COMPROBACIONES DE POSICIONES
        
        //System.out.println("ocupadas: "+ocupadas.toString());
        //System.out.println("repetidas: "+repetidas.toString());
        //System.out.println(posfinal.toString());
        //System.out.println(posfinal.get(1));
        
        //System.out.println("--------------------------------");
        for(int i=0;i<barcos.size();i++){
         System.out.println(barcos.get(i).toString());
        }
        do{
            System.out.println(barcos.size()+" "+barcos.toString());
            System.out.println("Dime posicion primero letra y despues numero");
            user=teclado.nextLine();
            posx=letraanum(user);
            posy=Integer.parseInt(user.substring(1));
            String posxy=posx+","+posy;
            System.out.println(posxy);
            if (posx==0 || posy>10 ){
                System.out.println("Tienes que irntroducir una posición válida" + posx+posy);
            }else{
                boolean comprobacion=false;
                for(int i=0;i<barcos.size();i++){
                    String barcopos=barcos.get(i).getPosicion();
                    if(barcopos.contains(posxy)==true){
                        tablero[posx][posy]="+";
                        System.out.println("Bien has dado a un barco");
                        comprobacion=true;
                        barcos.get(i).tocado(posx, posy);
                        if(barcos.get(i).posiciones()==barcos.get(i).getTamano()){
                            System.out.println("HUNDIDOOOOO!!");
                            System.out.println("Has hundido un "+barcos.get(i).getNombre());
                            for(int o=0;o<barcos.get(i).posicionesx.size();o++){
                                tablero[barcos.get(i).posicionesx.get(o)][barcos.get(i).posicionesy.get(o)]="X";
                            }
                            barcos.remove(i);
                        }
                    }
                }
                if(comprobacion==false){
                    System.out.println("Agua");
                    tablero[posx][posy]=Character.toString((char)126);
                }
            }
            mostartablero();
            intentos ++;
            if(barcos.size()==0){
                break;
            }
            
        }while(intentos!=25);
                
    }
    public static int letraanum(String user){
        if("A".equals(user.substring(0,1))||"a".equals(user.substring(0,1))){
            return 1;
        }else if("B".equals(user.substring(0,1))||"b".equals(user.substring(0,1))){
            return 2;
        }else if("C".equals(user.substring(0,1))||"c".equals(user.substring(0,1))){
            return 3;
        }else if("D".equals(user.substring(0,1))||"d".equals(user.substring(0,1))){
            return 4;
        }else if("E".equals(user.substring(0,1))||"e".equals(user.substring(0,1))){
            return 5;
        }else if("F".equals(user.substring(0,1))||"f".equals(user.substring(0,1))){
            return 6;
        }else if("G".equals(user.substring(0,1))||"g".equals(user.substring(0,1))){
            return 7;
        }else if("H".equals(user.substring(0,1))||"h".equals(user.substring(0,1))){
            return 8;
        }else if("I".equals(user.substring(0,1))||"i".equals(user.substring(0,1))){
            return 9;
        }else if("J".equals(user.substring(0,1))||"j".equals(user.substring(0,1))){
            return 10;
        }else{
            return 0;
        }
    }
    public static void mostartablero(){
        for(int i=0;i<11;i++){
            for(int o=0;o<11;o++){
                System.out.print(tablero[i][o]+"    ");//+"\t"
            }
            System.out.println("\t"+reglas[i]+"\n");
        }
    }
    public static void rellenarreglas(){
        reglas[0]="Reglas Básicas de la batalla";
        reglas[1]="El objetivo del juego es adivinar donde estan los barcos de tu oponente";
        reglas[2]="Cuando se te indique que digas una posición, tienes que decir una letra y un numero";
        reglas[3]="Simbología: ";
        reglas[4]="* - Cuadro oculto: no se sabe lo que hay, esa posicion no la has dicho";
        reglas[5]=Character.toString((char)126)+" - Esto es agua, has fallado y ahí no hay barco";
        reglas[6]="+ - Tocado: has dado a un barco, pero no le has hundido";
        reglas[7]="X - Hundido: cuando hayas tocado por completo al barco, este pasará a estar hundido";
        reglas[8]="FLOTA ENEMIGA:";
        reglas[9]="Un Portaaviones(Ocupa 4 espacios), Dos Acorazados(3 posiciones cada uno)";
        reglas[10]="Tres Buques(2 posiciones cada uno) y Cuatro Submarinos(1 posicion cada uno)";
    }
    //RELLENADO DEL TABLERO DE ASTERISCOS
    public static void rellenar(){
        char caracter=(char)42;
        for(int i=0;i<11;i++){
            for(int o=0;o<11;o++){
                tablero[i][o]=Character.toString(caracter);
            }
        }
    }
    //RELLENADO DE TABLERO CON NUMEOS Y LETRAS
    public static void rellenartablero(){
        int letras=65;
        int num=49;
        char numeros=(char)49;
        char letra=(char)65;
        for(int i=1;i<11;i++){
            tablero[0][i]=Character.toString(numeros);
            num=num+1;
            numeros=(char)num;
        }
        for(int i=1;i<11;i++){
            tablero[i][0]=Character.toString(letra);
            letras=letras+1;
            letra=(char)letras;
        }
        tablero[0][10]="10";
    }
    //GENERAMOS UNA ORIENTACIÓN CONTANDO CON LOS LIMITES
    public static void genenarbarcos(int logitud){
        int x=(int) (Math.random()*10+1);//derecha-izquierda numeros
        int y=(int) (Math.random()*10+1);//arriba-abajo letras
        int orientacion=(int) (Math.random()*2+1);
        String orientado="";
        if(x<5 && y<5){
            if(orientacion==1){
                orientado="derecha";
            }else{
                orientado="abajo";
            }
        }else if(x>5 && y>5){
            if(orientacion==1){
                orientado="izquierda";
            }else{
                orientado="arriba";
            }
        }else if(x>5 && y<5){
            orientado="arriba";
        }else if(x<5 && y>5){
            orientado="abajo";
        }else{
            if(orientacion==1){
                orientado="izquierda";
            }else{
                orientado="arriba";
            }
        }
        //System.out.println("Numero x: "+x+" Numero y: "+y+" Orientacion: "+orientacion+" Orientado: "+orientado);
        generarposiciones(x,y,orientado,logitud);
    }
    //COMPROBAMOS SI NO SE JUNTAN POSICIONES ENTRE LOS BARCOS Y GUARDAMOS SUS POSICIONES
    public static void generarposiciones(int x, int y, String orientado, int longitud){
        String posicionesfinal="";
        String posfallidas="";
        int conterr=0;
        if(orientado=="derecha"){
            for(int o=y;o<y+longitud;o++){
                if(comprobar(x,o)==true){
                    conterr=1;
                    repetidas.add(Integer.toString(x)+","+Integer.toString(o));
                }else{
                    ocupadas.add(Integer.toString(x)+","+Integer.toString(o));
                    posicionesfinal=posicionesfinal+Integer.toString(x)+","+Integer.toString(o)+" ";
                }
            }
        }
        if(orientado=="izquierda"){
            for(int o=y;o>y-longitud;o--){
                if(comprobar(x,o)==true){
                    conterr=1;
                    repetidas.add(Integer.toString(x)+","+Integer.toString(o));
                }else{
                    ocupadas.add(Integer.toString(x)+","+Integer.toString(o));
                    posicionesfinal=posicionesfinal+Integer.toString(x)+","+Integer.toString(o)+" ";  
                }
            }
        }
        if(orientado=="abajo"){
            for(int o=x;o<x+longitud;o++){
                if(comprobar(o,x)==true){
                    conterr=1;
                    repetidas.add(Integer.toString(x)+","+Integer.toString(o));
                }else{
                    ocupadas.add(" "+Integer.toString(o)+","+Integer.toString(x));
                    posicionesfinal=posicionesfinal+Integer.toString(o)+","+Integer.toString(x)+" ";
                }
            }
        }
        if(orientado=="arriba"){
            for(int o=x;o>x-longitud;o--){
                if(comprobar(o,x)==true){
                    conterr=1;
                    repetidas.add(Integer.toString(x)+","+Integer.toString(o));
                }else{
                    ocupadas.add(Integer.toString(o)+","+Integer.toString(x));
                    posicionesfinal=posicionesfinal+Integer.toString(o)+","+Integer.toString(x)+" "; 
                }
            }
        }
        if (conterr==1){
            genenarbarcos(longitud);
        }else{
            posfinal.add(posicionesfinal);
            //System.out.println(posicionesfinal);
        }
        //System.out.println(posfallidas);
    }
    public static boolean comprobar(int x,int o){
        String pos = Integer.toString(x)+","+Integer.toString(o);
        String pos2 = Integer.toString(x)+","+Integer.toString(o)+",";
        String pos3 = " "+Integer.toString(x)+","+Integer.toString(o)+",";
        String pos4 = " "+Integer.toString(x)+","+Integer.toString(o);
        if(ocupadas.contains(pos)==true || ocupadas.contains(pos2)==true || ocupadas.contains(pos3)==true ||ocupadas.contains(pos4)==true){
            return true;
        }else{
            return false;
        }
    }
}
