package net.yammyy.db;

public interface UsersFields {
    public static final String USERS_TABLE = "user";
    public static final String USERS_ID = "ID";
    public static final String USERS_LOGIN = "login";
    public static final String USERS_PASSWORD = "pwd";
    public static final String USERS_FAMILY_NAME = "F";
    public static final String USERS_NAME = "I";
    public static final String USERS_FATHER_NAME = "O";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_ROLE = "role";
    public static final String USERS_REGISTRATION_DATE = "date_reg";
    public static final String USERS_STANDARD_LANGUAGE = "stdln";
    public static final String USERS_STANDARD_CURRENCY = "stdcur";
    public static final String USERS_BLOCKED = "blocked";
    public static final String USERS_BLOCKING_TABLE = "user_blocking";
    public static final String USERS_BLOCKING_USER_ID = "u_id";
    public static final String USERS_BLOCKING_REASON = "reason";
    public static final String USERS_BLOCKING_DATE = "dt";
    public static final String ROLES_TABLE = "user_roles";
    public static final String ROLES_ID = "ID";
    public static final String ROLES_NAME = "name";

    public static final String BLOCKED_TABLE = "reason";
    public static final String BLOCKED_REASON_ID = "ID";
    public static final String BLOCKED_REASON_NAME = "name";
    public static final String SECURE_URLS_TABLE = "secure_urls";
    public static final String SECURE_URLS_ROLE_ID = "ROLE_ID";
    public static final String SECURE_URLS_URL = "url";
}
