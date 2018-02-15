package jp.techacademy.son.escape;

/**
 * Created by taiso on 2018/02/15.
 */


public class articleData {
    private String mContent;
    private String mCompanyName;
    private String mBlackName;
    private String mCase;
    private String mUid;
    private String mDate;
    private String mRef;
    private String mKey;


    public String getCompanyName(){
        return mCompanyName;
    }
    public String getBlackName(){
        return mBlackName;
    }
    public String getContent(){
        return mContent;
    }
    public String getCase(){
        return mCase;
    }
    public String getUid(){
        return mUid;
    }
    public String getDate(){
        return mDate;
    }
    public String getRef(){
        return mRef;
    }
    public String getKey(){
        return mKey;
    }

    public articleData(String uid,String date,String companyName, String blackName,String content,  String cases, String ref,String key) {
        mUid = uid;
        mDate = date;
        mCompanyName = companyName;
        mBlackName = blackName;
        mContent = content;
        mCase = cases;
        mRef = ref;
        mKey = key;
    }
}

