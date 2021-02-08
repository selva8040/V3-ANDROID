package com.elancier.healthzone.Pojo;

public class salarypo_app
{
    String id, fdate,reward,
    tdate,
    played,
    viewed,
    view_duplicate,
    not_seen,
    feedback_below4,
    feedback_above3,
    feedback_not,
    permission_below4,
    permission_above3,
    splayed,
    sviewed,
    sview_duplicate,
    snot_seen,
    sfeedback_below4,
    sfeedback_above3,
    sfeedback_not,
    spermission_below4,
    spermission_above3,
    frombank,
    tid,
    adate,
    reject,
    rejectmob,
    status,count,reason;

    public salarypo_app(String id, String fdate, String tdate, String played, String viewed, String view_duplicate, String not_seen, String feedback_below4, String feedback_above3, String feedback_not, String permission_below4,
                        String permission_above3, String splayed, String sviewed, String sview_duplicate, String snot_seen, String sfeedback_below4, String sfeedback_above3,
                        String sfeedback_not, String spermission_below4, String spermission_above3, String frombank, String tid, String adate,
                        String reject,String rejectmob, String status, String count, String reward,String reason) {

        this.fdate=fdate;
        this.id=id;
        this.tdate=tdate;
        this.played=played;
        this.viewed=viewed;
        this.view_duplicate=view_duplicate;
        this.not_seen=not_seen;
        this.feedback_below4=feedback_below4;
        this.feedback_above3=feedback_above3;
        this.feedback_not=feedback_not;
        this.permission_below4=permission_below4;
        this.permission_above3=permission_above3;
        this.splayed=splayed;
        this.sviewed=sviewed;
        this.sview_duplicate=sview_duplicate;
        this.snot_seen=snot_seen;
        this.sfeedback_below4=sfeedback_below4;
        this.sfeedback_above3=sfeedback_above3;
        this.sfeedback_not=sfeedback_not;
        this.spermission_below4=spermission_below4;
        this.spermission_above3=spermission_above3;
        this.frombank=frombank;
         this.tid=tid;
        this.adate=adate;
        this.reject=reject;
        this.rejectmob=rejectmob;
        this.status=status;
        this.count=count;
        this.reward=reward;
        this.reason=reason;

    }

    public String getfdate() {
        return fdate;
    }

    public String gettdate() {
        return tdate;
    }

    public String getReward() {
        return reward;
    }
    public String getReason() {
        return reason;
    }

    public String getfrombank() {return frombank;}
    public String gettid() {return tid;}
    public String getadate() {return adate;}
    public String getreject() {return reject;}
    public String getRejectmob() {return rejectmob;}
    public String getCount() {return count;}


    public String getstatus() {
                    return status;
                }
    public String getplayed() {
        return played;
    }
    public String getviewed() {
        return viewed;
    }
    public String getview_duplicate() {
        return view_duplicate;
    }
    public String getnot_seen() {
        return not_seen;
    }
    public String getfeedback_below4() {
        return feedback_below4;
    }

    public String getfeedback_above3() {
        return feedback_above3;
    }
    public String getfeedback_not() {
        return feedback_not;
    }

    public String getpermission_below4() {
        return permission_below4;
    }
    public String getpermission_above3() {
        return permission_above3;
    }
    public String getsplayed() {
        return splayed;
    }
    public String getsviewed() {
        return sviewed;
    }
    public String getsview_duplicate() {
        return sview_duplicate;
    }
    public String getsnot_seen() {
        return snot_seen;
    }
    public String getsfeedback_below4() {
        return sfeedback_below4;
    }
    public String getsfeedback_above3() {
        return sfeedback_above3;
    }
    public String getsfeedback_not() {
        return sfeedback_not;
    }

    public String getspermission_below4() {
    return spermission_below4;
}
    public String getspermission_above3() {
        return spermission_above3;
    }



}
