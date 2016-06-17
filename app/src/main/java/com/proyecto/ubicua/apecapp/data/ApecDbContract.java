package com.proyecto.ubicua.apecapp.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Define los nombres de las tablas y columnas de la base de datos ApecDb
 */
public class ApecDbContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class StudentEntry implements BaseColumns {

        public static final String TABLE_NAME = "student";
        public static final String COLUMN_ID = "Idstudent";
        public static final String COLUMN_NAME = "namestudent";
        public static final String COLUMN_REGNUMBER = "regnumber";
        public static final String COLUMN_PASS = "pass";
        public static final String COLUMN_GRADE_FK = "Idgrade";
        public static final String COLUMN_ADDRESS= "address";
        public static final String COLUMN_BIRTH = "birth";
        public static final String COLUMN_PHONE= "phone";
        public static final String COLUMN_PICTURE= "picture";

    }

    public static final class GradeEntry implements BaseColumns {

        public static final String TABLE_NAME = "grade";
        public static final String COLUMN_ID = "Idgrade";
        public static final String COLUMN_NAME = "grade";
    }

    public static final class RecordEntry implements BaseColumns{

        public static final String TABLE_NAME = "record";
        public static final String COLUMN_STUDENT_FK = "Idstudent";
        public static final String COLUMN_SUBJECT_FK = "Idsubject";
        public static final String COLUMN_QUARTER_FK = "Idquarter";
    }

    public static final class QuarterEntry implements BaseColumns{

        public static final String TABLE_NAME = "quarter";
        public static final String COLUMN_ID = "Idquarter";
        public static final String COLUMN_NAME = "quarter";
    }

    public static final class SubjectEntry implements BaseColumns{

        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_ID = "Idsubject";
        public static final String COLUMN_NAME = "subject";
    }

    public static final class BlockEntry implements BaseColumns {

        public static final String TABLE_NAME = "block";
        public static final String COLUMN_ID = "Idblock";
        public static final String COLUMN_SESSION_FK = "Idsession";
    }

    public static final class SessionEntry implements BaseColumns{

        public static final String TABLE_NAME = "session";
        public static final String COLUMN_ID = "Idsession";
        public static final String COLUMN_SUBJECT_FK = "Idsubject";
        public static final String COLUMN_CALENDAR = "calendar";
    }
}
