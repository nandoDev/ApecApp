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
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                " );";

        final String SQL_CREATE_QUARTER_TABLE = "CREATE TABLE " + QuarterEntry.TABLE_NAME + " (" +
                QuarterEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                " );";

        final String SQL_CREATE_SUBJECT_TABLE = "CREATE TABLE " + SubjectEntry.TABLE_NAME + " (" +
                QuarterEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                GradeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                " );";

        final String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " + StudentEntry.TABLE_NAME + " (" +
                StudentEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StudentEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                StudentEntry.COLUMN_REGNUMBER + " TEXT NOT NULL, " +
                StudentEntry.COLUMN_PASS + " TEXT NOT NULL, " +
                StudentEntry.COLUMN_BIRTH + " TEXT NOT NULL " +
                StudentEntry.COLUMN_ADDRESS + " TEXT NOT NULL " +
                //Clave foránea de la carrera que esta estudiando
                StudentEntry.COLUMN_GRADE_FK + " TEXT NOT NULL " +
                // Establecer la columna  grade como una clave foránea  a la tabla grade
                        " FOREIGN KEY (" + StudentEntry.COLUMN_GRADE_FK + ") REFERENCES " +
                        GradeEntry.TABLE_NAME + " (" + GradeEntry.COLUMN_ID + "), " +
                " );";

        final String SQL_CREATE_RECORDSTUDENT_TABLE = "CREATE TABLE " + RecordEntry.TABLE_NAME + " (" +
                //Clave foránea del estudiante
                RecordEntry.COLUMN_STUDENT_FK + " INTEGER NOT NULL " +
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
                SubjectEntry.TABLE_NAME + " (" + SubjectEntry.COLUMN_ID + "), " +
                " );";

        final String SQL_CREATE_BLOCK_TABLE = "CREATE TABLE " + BlockEntry.TABLE_NAME + " (" +
                BlockEntry.COLUMN_ID + " INTEGER NOT NULL " +
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

        final String SQL_INSERT_STUDENTS =
                "INSERT INTO student (nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES ('Carlos López','20131366','@123','15/12/1987', 'Calle Padre Billini #34', 'ADM');" +
                "INSERT INTO student (nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES ('Rodiel Montoya','20144578','@333','15/10/1992', 'Calle Leopoldo Navarro #8', 'ISC');" +
                "INSERT INTO student (nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES ('Cindy Flores','20121452','@222','04/12/1995', 'Av. Independencia #145', 'ISO');" +
                "INSERT INTO student (nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES ('Christian Alvárez','20124585','@111','08/12/1985', 'Calle Jacobo Majluta #34 Residencial El Vergel Ed.5 apto201', 'ISO');" +
                "INSERT INTO student (nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES ('Clara Vergara','20156235','@777','20/10/1988', 'Av. 27 de febrero #216', 'ADM');" +
                "INSERT INTO student (nameStudent, regnumber, pass, birth, address, grade)" +
                        " VALUES ('Rodrigo Ceara','20144512','@888','06/09/1986', 'Av. San Vicente Residencial AguasClaras. Edif C. Apto 3-2', 'ISO');" ;

        sqLiteDatabase.execSQL(SQL_CREATE_GRADE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_QUARTER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SUBJECT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RECORDSTUDENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SESSION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_BLOCK_TABLE);

        sqLiteDatabase.execSQL(SQL_INSERT_GRADES);
        sqLiteDatabase.execSQL(SQL_INSERT_STUDENTS);
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
