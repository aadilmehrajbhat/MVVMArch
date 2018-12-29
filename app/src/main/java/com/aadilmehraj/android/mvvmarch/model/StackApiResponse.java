package com.aadilmehraj.android.mvvmarch.model;

import java.util.List;

public class StackApiResponse {

    public class Owner{

        public int reputation;
        public long user_id;
        public String user_type;
        public int accept_rate;
        public String profile_image;
        public String display_name;
        public String link;
    }
   public class Item{

        public Owner owner;
        public Boolean is_accepted;
        public int score;
        public long last_activity_date;
        public long last_edit_date;
        public long creation_date;
        public long answer_id;
        public long question_id;
    }

    public List<Item> items;
    public boolean has_more;
    public int backoff;
    public int quota_max;
    public int quota_remaining;
}
