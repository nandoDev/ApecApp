package com.proyecto.ubicua.apecapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.proyecto.ubicua.apecapp.data.ApecDbContract.BlockEntry;
import com.proyecto.ubicua.apecapp.data.ApecDbContract.GradeEntry;
import com.proyecto.ubicua.apecapp.data.ApecDbContract.QuarterEntry;
import com.proyecto.ubicua.apecapp.data.ApecDbContract.RecordEntry;
import com.proyecto.ubicua.apecapp.data.ApecDbContract.SessionEntry;
import com.proyecto.ubicua.apecapp.data.ApecDbContract.StudentEntry;
import com.proyecto.ubicua.apecapp.data.ApecDbContract.SubjectEntry;


public class ApecDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    static final String DATABASE_NAME = "ApecAppDb.db";

    public ApecDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        final String SQL_CREATE_GRADE_TABLE = "CREATE TABLE " + GradeEntry.TABLE_NAME + " (" +
                GradeEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_QUARTER_TABLE = "CREATE TABLE " + QuarterEntry.TABLE_NAME + " (" +
                QuarterEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_SUBJECT_TABLE = "CREATE TABLE " + SubjectEntry.TABLE_NAME + " (" +
                QuarterEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " + StudentEntry.TABLE_NAME + " (" +
                StudentEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                StudentEntry.COLUMN_REGNUMBER + " TEXT NOT NULL, " +
                StudentEntry.COLUMN_PASS + " TEXT NOT NULL, " +
                StudentEntry.COLUMN_BIRTH + " TEXT NOT NULL, " +
                StudentEntry.COLUMN_ADDRESS + " TEXT NOT NULL, " +
                //Clave foránea de la carrera que esta estudiando
                StudentEntry.COLUMN_GRADE_FK + " TEXT NOT NULL, " +
                // Establecer la columna  grade como una clave foránea  a la tabla grade
                        " FOREIGN KEY (" + StudentEntry.COLUMN_GRADE_FK + ") REFERENCES " +
                        GradeEntry.TABLE_NAME + " (" + GradeEntry.COLUMN_ID + ") " +
                " );";

        final String SQL_CREATE_RECORDSTUDENT_TABLE = "CREATE TABLE " + RecordEntry.TABLE_NAME + " (" +
                //Clave foránea del estudiante
                RecordEntry.COLUMN_STUDENT_FK + " INTEGER NOT NULL, " +
                //Clave foránea de la materia cursada
                RecordEntry.COLUMN_SUBJECT_FK + " INTEGER NOT NULL, " +
                //Clave foránea del cuatrimestre en el que curso la materia
                RecordEntry.COLUMN_QUARTER_FK + " INTEGER NOT NULL, " +
                // Establecer la columna  Idstudent como una clave foránea  a la tabla student
                        " FOREIGN KEY (" + RecordEntry.COLUMN_STUDENT_FK + ") REFERENCES " +
                        StudentEntry.TABLE_NAME + " (" + StudentEntry.COLUMN_ID + "), " +
                // Establecer la columna  Idsubject como una clave foránea  a la tabla subject
                        " FOREIGN KEY (" + RecordEntry.COLUMN_SUBJECT_FK + ") REFERENCES " +
                        SubjectEntry.TABLE_NAME + " (" + SubjectEntry.COLUMN_ID + "), " +
                // Establecer la columna  Idquarter como una clave foránea  a la tabla quarter
                        " FOREIGN KEY (" + RecordEntry.COLUMN_SUBJECT_FK + ") REFERENCES " +
                        QuarterEntry.TABLE_NAME + " (" + QuarterEntry.COLUMN_ID + "), " +
                //Para  asegurar que las tres columnas IdStudent,IdSubject y IdQuarter conforman una
                // restricción UNIQUE con una estrategia REPLACE
                        " UNIQUE (" + RecordEntry.COLUMN_STUDENT_FK + ", " +
                              RecordEntry.COLUMN_SUBJECT_FK + ", " +
                              RecordEntry.COLUMN_QUARTER_FK + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_SESSION_TABLE = "CREATE TABLE " + SessionEntry.TABLE_NAME + " (" +
                SessionEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                //Clave foránea de la materia de la sesión
                SessionEntry.COLUMN_SUBJECT_FK + " INTEGER NOT NULL, " +
                SessionEntry.COLUMN_CALENDAR + " TEXT NOT NULL, " +
                // Establecer la columna  Idsubject como una clave foránea  a la tabla subject
                " FOREIGN KEY (" + SessionEntry.COLUMN_SUBJECT_FK + ") REFERENCES " +
                SubjectEntry.TABLE_NAME + " (" + SubjectEntry.COLUMN_ID + ") " +
                " );";

        final String SQL_CREATE_BLOCK_TABLE = "CREATE TABLE " + BlockEntry.TABLE_NAME + " (" +
                BlockEntry.COLUMN_ID + " INTEGER NOT NULL, " +
                //Clave foránea de la sesión
                BlockEntry.COLUMN_SESSION_FK + " INTEGER NOT NULL, " +
                // Establecer la columna  Idsession como una clave foránea  a la tabla session
                " FOREIGN KEY (" + BlockEntry.COLUMN_SESSION_FK + ") REFERENCES " +
                SessionEntry.TABLE_NAME + " (" + SessionEntry.COLUMN_ID + "), " +
                //Para  asegurar que las dos columnas Idblock y Idsession conforman una
                // restricción UNIQUE con una estrategia REPLACE
                " UNIQUE (" + BlockEntry.COLUMN_ID + ", " +
                BlockEntry.COLUMN_SESSION_FK + ") ON CONFLICT REPLACE);";


        final String SQL_INSERT_GRADES =
                "INSERT INTO grade VALUES ('IEL','INGENIERIA ELECTRONICA');" +
                "INSERT INTO grade VALUES ('IND','INGENIERIA INDUSTRIAL');" +
                "INSERT INTO grade VALUES ('DER','LICENCIATURA EN DERECHO');" +
                "INSERT INTO grade VALUES ('MER','LICENCIATURA EN MERCADOTECNIA');" +
                "INSERT INTO grade VALUES ('ADM','LICENCIATURA EN ADMINISTRACION DE EMPRESAS');" +
                "INSERT INTO grade VALUES ('PUB','LICENCIATURA EN PUBLICIDAD');" +
                "INSERT INTO grade VALUES ('ISC','LICENCIATURA EN SISTEMAS DE COMPUTACION');" +
                "INSERT INTO grade VALUES ('ISO','INGENIERIA DE SOFTWARE');" +
                "INSERT INTO grade VALUES ('INE','INGENIERIA ELECTRICA');";

        final String SQL_INSERT_SUBJECTS =
                "INSERT INTO subject VALUES ('ISO100','FUNDAMENTOS DE INFORMATICA Y ALGORITMOS');" +
                "INSERT INTO subject VALUES ('ISO105','FUNDAMENTOS DE SISTEMAS OPERATIVOS Y COMUNICACIONES');" +
                "INSERT INTO subject VALUES ('MAT126','MATEMATICA BASICA PARA INGENIERIA');" +
                "INSERT INTO subject VALUES ('ISO200','PROGRAMACION Y ESTRUCTURAS DE DATOS');" +
                "INSERT INTO subject VALUES ('MAT127','MATEMATICA SUPERIOR PARA INGENIERIA');" +
                "INSERT INTO subject VALUES ('SOC043','GESTION AMBIENTAL');" +
                "INSERT INTO subject VALUES ('ISO300','FUNDAMENTOS DE INGENIERIA DE SOFTWARE');" +
                "INSERT INTO subject VALUES ('ISO305','DISEÑO WEB I');" +
                "INSERT INTO subject VALUES ('MAT222','ALGEBRA LINEAL');" +
                "INSERT INTO subject VALUES ('ISO405','SISTEMAS OPERATIVOS AVANZADOS');" +
                "INSERT INTO subject VALUES ('INF164','BASE DE DATOS I');" +
                "INSERT INTO subject VALUES ('ADM091','ADMINISTRACION I');" +
                "INSERT INTO subject VALUES ('INF244','PROGRAMACION WEB I');" +
                "INSERT INTO subject VALUES ('ISO400','ANALISIS Y DISEÑO ORIENTADO A OBJETOS');" +
                "INSERT INTO subject VALUES ('ISO410','VERIFICACION Y VALIDACION DE SOFTWARE');" +
                "INSERT INTO subject VALUES ('INF165','BASE DE DATOS II');" +
                "INSERT INTO subject VALUES ('ISO500','INGENIERIA DE REQUISITOS');" +
                "INSERT INTO subject VALUES ('ISO505','INGENIERIA DE LA USABILIDAD');" +
                "INSERT INTO subject VALUES ('ISO600','ADMINISTRACION DE CONFIGURACION');" +
                "INSERT INTO subject VALUES ('ISO605','DESARROLLO DE SOFTWARE CON TECNOLOGIA PROPIETARIA I');" +
                "INSERT INTO subject VALUES ('ISO610','DESARROLLO DE SOFTWARE CON TECNOLOGIA OPEN SOURCE I');" +
                "INSERT INTO grade VALUES ('MAT151','MATEMATICA DISCRETA');";



        final String SQL_INSERT_QUARTERS =
                "INSERT INTO quarter VALUES ('1','2000-01');" +
                "INSERT INTO quarter VALUES ('2','2000-02');" +
                "INSERT INTO quarter VALUES ('3','2000-03');" +
                "INSERT INTO quarter VALUES ('4','2001-01');" +
                "INSERT INTO quarter VALUES ('5','2001-02');" +
                "INSERT INTO quarter VALUES ('6','2001-03');" +
                "INSERT INTO quarter VALUES ('7','2002-01');" +
                "INSERT INTO quarter VALUES ('8','2002-02');" +
                "INSERT INTO quarter VALUES ('9','2002-03');" +
                "INSERT INTO quarter VALUES ('11','2003-01');" +
                "INSERT INTO quarter VALUES ('12','2003-02');" +
                "INSERT INTO quarter VALUES ('13','2003-02');" +
                "INSERT INTO quarter VALUES ('14','2004-01');" +
                "INSERT INTO quarter VALUES ('15','2004-02');" +
                "INSERT INTO quarter VALUES ('16','2004-03');" +
                "INSERT INTO quarter VALUES ('17','2005-01');" +
                "INSERT INTO quarter VALUES ('18','2005-02');" +
                "INSERT INTO quarter VALUES ('19','2005-03');" +
                "INSERT INTO quarter VALUES ('20','2006-01');" +
                "INSERT INTO quarter VALUES ('21','2006-02');" +
                "INSERT INTO quarter VALUES ('22','2006-03');" +
                "INSERT INTO quarter VALUES ('23','2007-01');" +
                "INSERT INTO quarter VALUES ('24','2007-02');" +
                "INSERT INTO quarter VALUES ('25','2007-03');" +
                "INSERT INTO quarter VALUES ('26','2008-01');" +
                "INSERT INTO quarter VALUES ('27','2008-02');" +
                "INSERT INTO quarter VALUES ('28','2008-03');" +
                "INSERT INTO quarter VALUES ('29','2009-01');" +
                "INSERT INTO quarter VALUES ('30','2009-02');" +
                "INSERT INTO quarter VALUES ('31','2009-03');" +
                "INSERT INTO quarter VALUES ('32','2010-01');" +
                "INSERT INTO quarter VALUES ('33','2010-02');" +
                "INSERT INTO quarter VALUES ('34','2010-03');" +
                "INSERT INTO quarter VALUES ('35','2011-01');" +
                "INSERT INTO quarter VALUES ('36','2011-02');" +
                "INSERT INTO quarter VALUES ('37','2011-03');" +
                "INSERT INTO quarter VALUES ('38','2012-01');" +
                "INSERT INTO quarter VALUES ('39','2012-02');" +
                "INSERT INTO quarter VALUES ('40','2012-03');" +
                "INSERT INTO quarter VALUES ('41','2013-01');" +
                "INSERT INTO quarter VALUES ('42','2013-02');" +
                "INSERT INTO quarter VALUES ('43','2013-03');" +
                "INSERT INTO quarter VALUES ('44','2014-01');" +
                "INSERT INTO quarter VALUES ('45','2014-02');" +
                "INSERT INTO quarter VALUES ('46','2014-03');" +
                "INSERT INTO quarter VALUES ('47','2015-01');" +
                "INSERT INTO quarter VALUES ('48','2015-02');" +
                "INSERT INTO quarter VALUES ('49','2015-03');" +
                "INSERT INTO quarter VALUES ('50','2016-01');" +
                "INSERT INTO quarter VALUES ('51','2016-02');" +
                "INSERT INTO quarter VALUES ('52','2016-03');" +
                "INSERT INTO quarter VALUES ('53','2017-01');";



        final String SQL_INSERT_STUDENTS =
                "INSERT INTO student (Idstudent, nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES (1,'Carlos López','20131366','@123','15/12/1987', 'Calle Padre Billini #34', 'ADM');" +
                "INSERT INTO student (Idstudent,nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES (2,'Rodiel Montoya','20144578','@333','15/10/1992', 'Calle Leopoldo Navarro #8', 'ISC');" +
                "INSERT INTO student (Idstudent,nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES (3,'Manuel Flores','20121452','@222','04/12/1995', 'Av. Independencia #145', 'ISO');" +
                "INSERT INTO student (Idstudent,nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES (4,'Christian Alvárez','20124585','@111','08/12/1985', 'Calle Jacobo Majluta #34 Residencial El Vergel Ed.5 apto201', 'ISO');" +
                "INSERT INTO student (Idstudent,nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES (5,'Clara Vergara','20156235','@777','20/10/1988', 'Av. 27 de febrero #216', 'ADM');" +
                "INSERT INTO student (Idstudent,nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES (6,'Rodrigo Ceara','20144512','@888','06/09/1986', 'Av. San Vicente Residencial AguasClaras. Edif C. Apto 3-2', 'ISO');" ;

        final String SQL_INSERT_RECORD =
                "INSERT INTO record VALUES ('3','ISO100','38');" +
                        "INSERT INTO record VALUES ('3','ISO105','38');" +
                        "INSERT INTO record VALUES ('3','MAT126','38');" +
                        "INSERT INTO record VALUES ('3','ISO200','39');" +
                        "INSERT INTO record VALUES ('3','MAT127','39');" +
                        "INSERT INTO record VALUES ('3','SOC043','39');" +
                        "INSERT INTO record VALUES ('3','ISO300','40');" +
                        "INSERT INTO record VALUES ('3','ISO305','40');" +
                        "INSERT INTO record VALUES ('3','MAT222','40');" +
                        "INSERT INTO record VALUES ('3','ISO405','41');" +
                        "INSERT INTO record VALUES ('3','INF164','41');" +
                        "INSERT INTO record VALUES ('3','ADM091','41');" +
                        "INSERT INTO record VALUES ('3','INF244','42');" +
                        "INSERT INTO record VALUES ('3','ISO400','42');" +
                        "INSERT INTO record VALUES ('3','ISO410','42');" +
                        "INSERT INTO record VALUES ('3','INF165','43');" +
                        "INSERT INTO record VALUES ('3','ISO500','43');" +
                        "INSERT INTO record VALUES ('3','ISO505','43');" +
                        "INSERT INTO record VALUES ('3','ISO600','44');" +
                        "INSERT INTO record VALUES ('3','ISO605','44');" +
                        "INSERT INTO record VALUES ('3','ISO610','44');" ;

        sqLiteDatabase.execSQL(SQL_CREATE_GRADE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_QUARTER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SUBJECT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RECORDSTUDENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SESSION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BLOCK_TABLE);

        sqLiteDatabase.execSQL(SQL_INSERT_GRADES);
        sqLiteDatabase.execSQL(SQL_INSERT_SUBJECTS);
        sqLiteDatabase.execSQL(SQL_INSERT_QUARTERS);
        sqLiteDatabase.execSQL(SQL_INSERT_STUDENTS);
        sqLiteDatabase.execSQL(SQL_INSERT_RECORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
