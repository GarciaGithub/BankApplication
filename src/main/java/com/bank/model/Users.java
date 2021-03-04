package com.bank.model;

public class Users {
 private int id;
 private String firstName;
 private String LastName;
 private String username;
 private String password;
 private String type;
public Users() {
	super();
	// TODO Auto-generated constructor stub
}


public Users(String username, String password, String type) {
	super();
	this.username = username;
	this.password = password;
	this.type = type;
}



public Users(String username, String password) {
	super();
	this.username = username;
	this.password = password;
}


public Users(int id, String firstName, String lastName, String type) {
	super();
	this.id = id;
	this.firstName = firstName;
	LastName = lastName;
	this.type = type;
}

public Users(int id, String firstName, String lastName, String username, String password, String type) {
	super();
	this.id = id;
	this.firstName = firstName;
	LastName = lastName;
	this.username = username;
	this.password = password;
	this.type = type;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return LastName;
}
public void setLastName(String lastName) {
	LastName = lastName;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
@Override
public String toString() {
	return "Users [id=" + id + ", firstName=" + firstName + ", LastName=" + LastName + ", username=" + username
			+ ", password=" + password + ", type=" + type + "]";
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((LastName == null) ? 0 : LastName.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + id;
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Users other = (Users) obj;
	if (LastName == null) {
		if (other.LastName != null)
			return false;
	} else if (!LastName.equals(other.LastName))
		return false;
	if (firstName == null) {
		if (other.firstName != null)
			return false;
	} else if (!firstName.equals(other.firstName))
		return false;
	if (id != other.id)
		return false;
	if (password == null) {
		if (other.password != null)
			return false;
	} else if (!password.equals(other.password))
		return false;
	if (type == null) {
		if (other.type != null)
			return false;
	} else if (!type.equals(other.type))
		return false;
	if (username == null) {
		if (other.username != null)
			return false;
	} else if (!username.equals(other.username))
		return false;
	return true;
}
 

 
}
