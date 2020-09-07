package com.twu;

public class HotSearch {
//    热搜排名，热搜描述，热搜的热度(票数)
    private String name;
    private int rank;
    private String description;
    private int vote;
    private int isSuperHotSearch;//0普通热搜，1超级热搜
    private int buyMoney;

    public HotSearch(){}

    public HotSearch(String name) {
        this.name = name;
        this.rank = Integer.MAX_VALUE;
    }

    public HotSearch(String name, int rank, String description, int vote, int isSuperHotSearch, int buyMoney) {
        this.name = name;
        this.rank = rank;
        this.description = description;
        this.vote = vote;
        this.isSuperHotSearch = isSuperHotSearch;
        this.buyMoney = buyMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int isSuperHotSearch() {
        return isSuperHotSearch;
    }

    public void setSuperHotSearch(int superHotSearch) {
        isSuperHotSearch = superHotSearch;
    }

    public int getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(int money) {
        this.buyMoney = money;
    }

    @Override
    public String toString() {
        return name +
                "," + rank +
                "," + description +
                "," + vote +
                "," + isSuperHotSearch +
                "," + buyMoney ;
    }
}