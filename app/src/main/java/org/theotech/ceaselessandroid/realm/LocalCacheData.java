package org.theotech.ceaselessandroid.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by uberx on 10/4/15.
 */
public class LocalCacheData extends RealmObject {
    @PrimaryKey @Required
    private String creationDate;
    private String scriptureCitation;
    private String scriptureText;
    private String scriptureLink;
    private String scriptureJson;
    private String verseImageURL;
    private RealmList<RealmString> personIdsToPrayFor;
    private Integer pageIndex;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getScriptureCitation() {
        return scriptureCitation;
    }

    public void setScriptureCitation(String scriptureCitation) {
        this.scriptureCitation = scriptureCitation;
    }

    public String getScriptureText() {
        return scriptureText;
    }

    public void setScriptureText(String scriptureText) {
        this.scriptureText = scriptureText;
    }

    public String getScriptureLink() {
        return scriptureLink;
    }

    public void setScriptureLink(String scriptureLink) {
        this.scriptureLink = scriptureLink;
    }

    public String getScriptureJson() {
        return scriptureJson;
    }

    public void setScriptureJson(String scriptureJson) {
        this.scriptureJson = scriptureJson;
    }

    public String getVerseImageURL() {
        return verseImageURL;
    }

    public void setVerseImageURL(String verseImageURL) {
        this.verseImageURL = verseImageURL;
    }

    public RealmList<RealmString> getPersonIdsToPrayFor() {
        return personIdsToPrayFor;
    }

    public void setPersonIdsToPrayFor(RealmList<RealmString> personIdsToPrayFor) {
        this.personIdsToPrayFor = personIdsToPrayFor;
    }

    public Integer getPageIndex() { return pageIndex; }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
