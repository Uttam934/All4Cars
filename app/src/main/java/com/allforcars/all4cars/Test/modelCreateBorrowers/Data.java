
package com.allforcars.all4cars.Test.modelCreateBorrowers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("borrowers")
    @Expose
    private List<Borrower> borrowers = null;
    @SerializedName("borrower_ids")
    @Expose
    private List<Integer> borrowerIds = null;

    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    public List<Integer> getBorrowerIds() {
        return borrowerIds;
    }

    public void setBorrowerIds(List<Integer> borrowerIds) {
        this.borrowerIds = borrowerIds;
    }

}
