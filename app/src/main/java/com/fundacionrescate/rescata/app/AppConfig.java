package com.fundacionrescate.rescata.app;

public class AppConfig {
    // Server user login url
//    public static String HOST = "http://192.168.0.106:7070/";
//    public static String HOST = "http://192.168.1.116:7070/";
//    public static String HOST = "http://51.77.148.251:7070/";


//    public static String IP = "http://192.168.0.107";
    public static String IP = "http://144.217.241.211";
    public static String HOST = IP+":7070/";
    public static String HOST_UPLOAD = "http://144.217.241.211/";
//      public static String HOST = "http://192.168.100.11:7070/";
//	    public static String HOST = "http://144.217.86.189:7070/";
//      public static String HOST_IMAGE = "http://144.217.86.189";
//      public static String HOST_IMAGE = " http://51.77.148.251/RescateAnimal/admin/";
    public static String HOST_IMAGE =  "http://144.217.241.211/RescateAnimal/admin/";


    //	public static String HOST = "http://192.168.1.116/wsbases25660/";
    public static String URL_ESPECIE = HOST+"api/especie";
    public static String URL_SEXO = HOST+"api/sexo";
    public static String URL_RAZA = HOST+"api/raza";
    public static String URL_REPORTE = HOST+"api/reportar/new";
    public static String URL_REPORTE_IMAGE = HOST+"api/reportar/uploadImage/";
    public static String URL_REPORTE_UPDATE = HOST+"api/reportar/update/";
    public static String URL_LIST_REPORTE = HOST+"api/reportar";

    public static String URL_USUARIO = HOST+"api/usuario/new";
    public static String URL_USUARIO_RECOVERY = HOST+"api/usuario/recovery";
    public static String URL_USUARIO_VALIDA = HOST+"api/usuario/valida";
    public static String URL_ADOPCIONES = HOST+"api/adopciones";
    public static String URL_ADOPCIONES_POSTULAR = HOST+"api/postular/new";
    public static String URL_PRODUCTOS= HOST+"api/productos";
    public static String URL_SALUD= HOST+"api/salud";
    public static String URL_DONACION= HOST+"api/donacion";
    public static String URL_COMENTARIOS= HOST+"api/comentarios";
    public static String URL_EVENTO= HOST+"api/evento";
    public static String URL_EVENTO_PARTICIPANTE= HOST+"api/participante/new";


    public static String PREF_USUARIO = "Usuario";
    public static String PREF_isLOGGED = "Logged";







}
