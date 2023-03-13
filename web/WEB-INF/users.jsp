<%-- 
    Document   : users
    Created on : 11-Mar-2023, 2:22:03 PM
    Author     : Swift
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>

        <c:choose>
            <c:when test="${users.isEmpty()}">
                <p><strong>No users found. Please add a user.</strong>
                </c:when>
                <c:otherwise>
                <table border="1">
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.role.roleName}</td>
                            <td><a href="<c:url value='/user?action=edit&amp;email=${user.email}' />">Edit</a></td>
                            <td><a href="<c:url value='/user?action=delete&amp;email=${user.email}' />">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${user_selected ne null}">
                <h2>Edit User</h2>
                <form action="" method="POST">
                    Email: ${user_selected.email}<br>
                    First name: <input type="text" name="firstname" value="${user_selected.firstName}"><br>
                    Last name: <input type="text" name="lastname" value="${user_selected.lastName}"><br>
                    Password: <input type="password" name="password"><br>
                    Role: <select name="role">
                        <option value="1">system admin</option>
                        <option value="2">regular user</option>
                    </select><br>
                    <input type="submit" name="update" value="Update">
                    <input type="submit" name="cancel" value="Cancel">
                </form>
                ${message}
            </c:when>
            <c:otherwise>
                <h2>Add User</h2>
                <form action="" method="POST">
                    Email: <input type="email" name="email"><br>
                    First name: <input type="text" name="firstname"><br>
                    Last name: <input type="text" name="lastname"><br>
                    Password: <input type="password" name="password"><br>
                    Role: <select name="role">
                        <option value="1">system admin</option>
                        <option value="2">regular user</option>
                    </select><br>
                    <input type="hidden" name="action" value="add">
                    <input type="submit" value="Add User">
                </form>
                ${message}
            </c:otherwise>
        </c:choose>
    </body>
</html>