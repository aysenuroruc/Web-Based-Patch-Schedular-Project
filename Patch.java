package com.spring.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Patch {

	@GraphId
	private Long id;

	private String name;

	@Relationship(direction = Relationship.INCOMING, type = "PATCHES")
	private Release release;

	private String features;

	private int cutOfWeek;
	private int cutOfYear;

	private Date cutOfDate;

	private int sanityStartWeek;

	private int sanityEndWeek;
	
	private int sanityStartYear;
	private int sanityEndYear;

	private Date vDate;

	private String color;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public Date getCutOfDate() {
		return cutOfDate;
	}

	public void setCutOfDate(Date cutOfDate) {
		this.cutOfDate = cutOfDate;
	}

	public Date getvDate() {
		return vDate;
	}

	public void setvDate(Date vDate) {
		this.vDate = vDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getCutOfWeek() {
		return cutOfWeek;
	}

	public void setCutOfWeek(int cutOfWeek) {
		this.cutOfWeek = cutOfWeek;
	}

	public int getSanityStartWeek() {
		return sanityStartWeek;
	}

	public void setSanityStartWeek(int sanityStartWeek) {
		this.sanityStartWeek = sanityStartWeek;
	}

	public int getSanityEndWeek() {
		return sanityEndWeek;
	}

	public void setSanityEndWeek(int sanityEndWeek) {
		this.sanityEndWeek = sanityEndWeek;
	}

	public int getCutOfYear() {
		return cutOfYear;
	}

	public void setCutOfYear(int cutOfYear) {
		this.cutOfYear = cutOfYear;
	}

	public int getSanityStartYear() {
		return sanityStartYear;
	}

	public void setSanityStartYear(int sanityStartYear) {
		this.sanityStartYear = sanityStartYear;
	}

	public int getSanityEndYear() {
		return sanityEndYear;
	}

	public void setSanityEndYear(int sanityEndYear) {
		this.sanityEndYear = sanityEndYear;
	}

}