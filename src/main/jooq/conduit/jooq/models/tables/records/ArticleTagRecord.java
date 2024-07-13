/*
 * This file is generated by jOOQ.
 */
package conduit.jooq.models.tables.records;


import conduit.jooq.models.tables.ArticleTag;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArticleTagRecord extends UpdatableRecordImpl<ArticleTagRecord> implements Record3<Long, Long, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.article_tag.article_id</code>.
     */
    public void setArticleId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.article_tag.article_id</code>.
     */
    public Long getArticleId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.article_tag.tag_id</code>.
     */
    public void setTagId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.article_tag.tag_id</code>.
     */
    public Long getTagId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.article_tag.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.article_tag.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, LocalDateTime> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return ArticleTag.ARTICLE_TAG.ARTICLE_ID;
    }

    @Override
    public Field<Long> field2() {
        return ArticleTag.ARTICLE_TAG.TAG_ID;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return ArticleTag.ARTICLE_TAG.CREATED_AT;
    }

    @Override
    public Long component1() {
        return getArticleId();
    }

    @Override
    public Long component2() {
        return getTagId();
    }

    @Override
    public LocalDateTime component3() {
        return getCreatedAt();
    }

    @Override
    public Long value1() {
        return getArticleId();
    }

    @Override
    public Long value2() {
        return getTagId();
    }

    @Override
    public LocalDateTime value3() {
        return getCreatedAt();
    }

    @Override
    public ArticleTagRecord value1(Long value) {
        setArticleId(value);
        return this;
    }

    @Override
    public ArticleTagRecord value2(Long value) {
        setTagId(value);
        return this;
    }

    @Override
    public ArticleTagRecord value3(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public ArticleTagRecord values(Long value1, Long value2, LocalDateTime value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ArticleTagRecord
     */
    public ArticleTagRecord() {
        super(ArticleTag.ARTICLE_TAG);
    }

    /**
     * Create a detached, initialised ArticleTagRecord
     */
    public ArticleTagRecord(Long articleId, Long tagId, LocalDateTime createdAt) {
        super(ArticleTag.ARTICLE_TAG);

        setArticleId(articleId);
        setTagId(tagId);
        setCreatedAt(createdAt);
        resetChangedOnNotNull();
    }
}