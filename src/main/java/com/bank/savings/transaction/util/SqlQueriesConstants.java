package com.bank.savings.transaction.util;

public class SqlQueriesConstants {
    public static final String GET_OFFICIAL_DATA =  "select ifsc_code, user_id, sm_id, pan_id " +
                                                    "from  bank_savings_accounts where cif_id = ? and account_no = ? ;";

    public static final String GET_CURRENT_BALANCE =    "select coalesce" +
                                                        "(" +
                                                            "(select balance from savings_accounts_transaction " +
                                                            "where cif_id = ? and account_no = ? order by transaction_time desc limit 1), 0" +
                                                        ")" +
                                                        " AS latest_balance;";

    public static final String GET_TRANSACTION_INFO =   "select transaction_time, transaction_id, transaction_type, deposits, transfer_deposit, withdrawals, " +
                                                        "transfer_deduction, interest, cr, dr, balance, atm_location " +
                                                        "from savings_accounts_transaction where cif_id = ? and account_no = ? " +
                                                        "order by transaction_time desc;";

    public static final String TRANSACTION_WITHDRAWALS =    "insert into savings_accounts_transaction " +
                                                            "(transaction_id, cif_id, ifsc_code, user_id, sm_id, account_no," +
                                                            "transaction_type, dr, withdrawals, balance, transaction_time,cr, transfer_deduction, transfer_deposit, deposits, interest) " +
                                                            "values (?,?,?,?,?,?,'BANK',?,?,?, current_timestamp(),0.0,0.0,0.0,0.0,0.0);";

    public static final String TRANSACTION_DEPOSITS =   "insert into savings_accounts_transaction " +
                                                        "(transaction_id, cif_id, ifsc_code, user_id, sm_id, account_no," +
                                                        "transaction_type, cr, deposits, balance, transaction_time,dr, transfer_deduction, transfer_deposit, withdrawals, interest) " +
                                                        "values (?,?,?,?,?,?,'BANK',?,?,?, current_timestamp(),0.0,0.0,0.0,0.0,0.0);";

    public static final String INTEREST_CREDIT =    "insert into savings_accounts_transaction " +
                                                    "(transaction_id, cif_id, ifsc_code, user_id, sm_id, account_no," +
                                                    "cr, balance, interest, transaction_type, dr, transfer_deduction, transfer_deposit, withdrawals, deposits,transaction_time) " +
                                                    "values (?,?,?,?,?,?,?,?,?,'BANK',0.0,0.0,0.0,0.0,0.0,current_timestamp());";

    public static final String TRANSFER_DEDUCTION = "insert into savings_accounts_transaction " +
                                                    "(transaction_id, cif_id, ifsc_code, user_id, sm_id, account_no," +
                                                    "transaction_type, dr, transfer_deduction, balance, transaction_time,cr, interest, transfer_deposit, withdrawals, deposits) " +
                                                    "values (?,?,?,?,?,?,'BANK',?,?,?, current_timestamp(),0.0,0.0,0.0,0.0,0.0);";

    public static final String TRANSFER_DEPOSITS =  "insert into savings_accounts_transaction " +
                                                    "(transaction_id, cif_id, ifsc_code, user_id, sm_id, account_no," +
                                                    "transaction_type, cr, transfer_deposit, balance, transaction_time, dr, interest, transfer_deduction, withdrawals, deposits) " +
                                                    "values (?,?,?,?,?,?,'BANK',?,?,?, current_timestamp(),0.0,0.0,0.0,0.0,0.0);";

    public static final String TRANSACTION_DUPLICATE = "select count(*) as transaction_duplicate from savings_accounts_transaction where transaction_id = ? ;";

    public static final String ACCOUNT_EXISTS = "select count(*) as account_exists from bank_savings_accounts where cif_id = ? and account_no = ? ;";

    public static final String ATM_TRANSACTION =    "insert into savings_accounts_transaction " +
                                                    "(transaction_id, cif_id, ifsc_code, user_id, sm_id, account_no," +
                                                    "transaction_type, dr, withdrawals, balance, atm_location ,transaction_time,cr, interest, transfer_deduction, transfer_deposit, deposits) " +
                                                    "values (?,?,?,?,?,?,'ATM',?,?,?,?, current_timestamp(),0.0,0.0,0.0,0.0,0.0);";

}
