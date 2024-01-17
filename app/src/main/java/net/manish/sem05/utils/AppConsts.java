package net.manish.sem05.utils;

import java.util.HashMap;

public interface AppConsts {

    String PAPER_DATA = "paper_data";
    String FROM_FRAG = "from_frag";
    String VERSION_CODE = "versionCode";
    String IMAGE_GIF_URL = "image_gif_url";
    String BATCH_ID = "batch_id";
    String HOME_WORK_DATE = "homework_date";
    String TOKEN = "token";
    String IS_PUSH = "push";
    String APP_INFO = "sitesetting";

    void setIdsOfAllSelectedQuestions(HashMap<String, String> hasIdsAns, HashMap<String, String> questionTimeHash);

    String MAIN_URl = "https://kamleshyadav.com/e-academy_app"; //  put your server base url here thanks..
    String BASE_URL = MAIN_URl + "/api/";
    String URL_PRIVACY_POLICY = MAIN_URl+"/privacyandpolicy";
    String STUDENT_DATA = "student_data";
    String API_LIVE_CLASS_DATA = "home/getLiveClassData";
    String checkActiveLiveClassJetsi = "home/checkActiveLiveClassJetsi";
    String API_DB_NEW_CHANGES_ALL_BATCH = "home/db_new_changes_all_batch";
    String API_RESET_PASSWORD = "Home/reset_password";
    String API_SYLLABUS_DATA = "home/get_syllabus_data";
    String API_NOTES_PDF = "Home/list_book_notes_paper";
    String API_CHECKLANGUAGE = "home/checkLanguage";
    String API_HOME_GET_SUB = "home/get_subject";
    String API_HOME_DB_NEW_CHANGES = "home/db_new_changes";
    String API_HOME_LAST_LOGIN_TIME = "home/last_login_time";
    String API_HOME_GET_CHAPTER = "home/get_chapter";
    String API_VACANCIES = "home/viewVacancy";
    String API_PLAY_VIDEO = "home/video_lecture";
    String API_NOTIFICATION_MERGED = "notice/notice_merged";
    String API_NOTIFICATION_KBC = "home/notices";
    String API_WELCOME = "home/app_version";
    String API_GET_BATCH_FEE = "home/get_batch_fee";
    String API_get_all_data = "home/get_all_data";
    String API_CHANGE_BATCH = "home/changeMyBatch";
    String API_VIEW_SUBJECT_LIST = "home/view_subject_list";
    String API_STUDENT_REGISTRATION = "home/student_registration";
    String API_HOMEGENERAL_SETTING = "home/general_setting";
    String API_DOUBTS_CLASS_ASK = "home/doubts_class_ask";
    String API_PAY_BATCH_FEE = "home/pay_batch_fee";
    String API_GET_DOUBTS_ASK = "home/get_doubts_ask";
    String API_GET_PAYMENT_HISTORY = "home/get_payment_history";
    String API_PRACTICE_PAPER = "exam/exam_paper";
    String API_GENERATE_PRACTICE_PAPER = "exam/get_exam_paper";
    String API_HOME_PROFILE_UPDATE = "home/profile_update";
    String API_PRACTICE_TEST_RESULT = "exam/exam_result";
    String API_GET_ATTENDANCE = "home/getAttendance";
    String API_APPLY_LEAVE = "home/apply_leave";
    String API_VIEW_LEAVE = "home/view_leave";
    String API_GET_ACADEMIC_RECORD = "home/getAcademicRecord";
    String API_CHECK_BATCH = "home/check_batch";
    String API_VIEW_PRACTICE_ANS_SHEET = "exam/view_ans_sheet";
    String API_LOGIN = "home/login";
    String API_LOGOUT = "home/logout";
    String API_CHECK_ACTIVE_LIVE_CLASS = "Home/checkActiveLiveClass";
    String API_CERTIFICATE = "home/certificate";
    String API_GET_TOP_SCORER = "home/getTopScorer";
    String API_GET_RESULT = "exam/submit_paper";
    String HOME_WORK = "home/Homework";
    String EXTRA_CLASS = "home/extraClass";
    String CHECK_LOGIN = "home/chekLogin";
    String TRUE = "true";
    String STATUS = "status";
    String VACANCY_ID = "vacancy_id";
    String STUDENT_ID = "student_id";
    String NAME = "name";
    String MONTH = "month";
    String AMOUNT = "amount";
    String TRANSACTION_ID = "transaction_id";
    String TRANSACTION_INFO = "transaction_info";
    String PAGE_NO = "page_no";
    String YEAR = "year";
    String TYPE = "type";
    String IS_SPLASH = "is_splash";
    String PAPER_TIME = "paper_time";
    String EXAM_NAME = "exam_name";
    String SHOW_RESULT = "show_result";
    String SEARCH = "search";
    String LIMIT = "limit";
    String LENGTH = "length";
    String START = "start";


    String TOTAL_QUESTIONS = "total_questions";
    String TOTAL_ATTEMPT = "total_attempt";
    String CORRECT_ANSWERS = "correct_answers";
    String WRONG_ANSWERS = "wrong_answers";
    String MINUS_MARKS = "minus_marks";
    String TOTAL_SCORE = "total_score";
    String negetive = "negetive";
    String PERCENTAGE = "percentage";
    String START_TIME = "start_time";
    String SUBMIT_TIME = "submit_time";
    String DATE = "date";
    String USERNAME = "username";
    String PASSWORD = "password";
    String IMAGE = "image";
    String SUBJECT_ID = "subject_id";


    String TIME_TAKEN = "time_taken";
    String PAPER_ID = "paper_id";
    String PAPER_NAME = "paper_name";
    String EXAM_TYPE = "exam_type";
    String TIME_USED = "time_used";
    String RESULT_ID = "result_id";
    String TOTAL_QUESTION = "total_question";
    String EXAM_DATE = "exam_date";
    String IS_REGISTER = "is_register";
    String IS_ADMIN = "admin_id";
    String UID = "uid";
    String FROM_DATE = "from_date";
    String TO_DATE = "to_date";
    String SUBJECT = "subject";
    String LEAVE_MSG = "leave_msg";
    String TEACHER_ID = "teacher_id";
    String DESCRIPTION = "description";
    String CHAPTER_ID = "chapter_id";
    String MOBILE = "mobile";
    String EMAIL = "email";
    String ISEMAILEXIST = "isEmailExist";


}
