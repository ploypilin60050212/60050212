package th.co.cdgs.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import th.co.cdgs.bean.CustomerDto;

import javax.ws.rs.Path;
@Path("customer")
public class CustomerController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer() throws SQLException{
		List<CustomerDto> list = new ArrayList<CustomerDto>();
		ResultSet rs = null;
		CustomerDto customerDto = null;
		PreparedStatement pst = null;
		Connection conn = null;
		
		try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop","root","p@ssw0rd");
		pst = conn.prepareStatement("SELECT customer_id,"
				+"CONCAT(first_name,' ',last_name) as full_name,"
				+"address,tel,email FROM customer");
		rs = pst.executeQuery();
		customerDto = null;
		while(rs.next()) {
			customerDto = new CustomerDto();
			customerDto.setCastomerId(rs.getLong("customer_id"));
			customerDto.setFullName(rs.getString("full_name"));
			customerDto.setAddress(rs.getString("address"));
			customerDto.setTel(rs.getString("tel"));
			customerDto.setEmail(rs.getString("email"));
			list.add(customerDto);
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
			
				rs.close();
				
			}
			if(pst != null) {
				
				pst.close();
				
			}
			if(conn != null) {
				
				conn.close();
				
			}
		}
				return Response.ok().entity(list).build();
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(CustomerDto customer) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/workshop", "root", "root");
			pst = conn.prepareStatement(
					"INSERT INTO customer  (first_name ,last_name,address,tel , email) "
					+ "VALUES (? ,? ,? ,? ,?)");
			int index = 1;
			pst.setString(index++, customer.getFirstName());
			pst.setString(index++, customer.getLastName());
			pst.setString(index++, customer.getAddress());
			pst.setString(index++, customer.getTel());
			pst.setString(index++, customer.getEmail());
			pst.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return Response.status(Status.CREATED).entity(customer).build();
	}





	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(CustomerDto customer) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/workshop", "root", "root");
			pst = conn.prepareStatement(
					"UPDATE customer  SET " + 
					"first_name  = ? ," + 
					"last_name = ? ," + 
					"address = ? , " + 
					"tel= ?  , " + 
					"email = ? " + 
					"WHERE customer_Id = ? ");
			int index = 1;
			pst.setString(index++, customer.getFirstName());
			pst.setString(index++, customer.getLastName());
			pst.setString(index++, customer.getAddress());
			pst.setString(index++, customer.getTel());
			pst.setString(index++, customer.getEmail());
			pst.setNString(index++, customer.getCustomerId());
			pst.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return Response.status(Status.OK).entity(customer).build();
	}
}
