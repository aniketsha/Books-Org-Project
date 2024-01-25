package BookStoreAssignmentAniketSharma.assignment3.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import BookStoreAssignmentAniketSharma.assignment3.beans.Books;
import BookStoreAssignmentAniketSharma.assignment3.beans.User;

@Repository
public class DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
// Method to find a user account by email
	public User findUserAccount(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where email = :email";
		namedParameters.addValue("email", email);
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}

// Method to get User Roles for a specific User id
	public List<String> getRolesById(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId " + "AND userId = :userId";
		namedParameters.addValue("userId", userId);
		return jdbc.queryForList(query, namedParameters, String.class);
	}
	
	public void addUser(String email, String password) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO sec_user "
		+ "(email, encryptedPassword, enabled) "
		+ "VALUES (:email, :encryptedPassword, 1)";
		namedParameters.addValue("email", email);
		namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, namedParameters);
		}
	
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_role (userId, roleId) "
		+ "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);
		jdbc.update(query, namedParameters);
		}

	public void insertBook(Books book) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("bookName", book.getBookName()); // Adding a named parameter
		namedParameters.addValue("isbn", book.getISBN());
		namedParameters.addValue("price", book.getPrice());
		namedParameters.addValue("id", book.getId());
		namedParameters.addValue("description", book.getDescription());

		String query = "INSERT INTO book(bookName, ISBN, price, description) VALUES (:bookName,:isbn,:price,:description)";

		int rowsAffected = jdbc.update(query, namedParameters);

		if (rowsAffected > 0)
			System.out.println("book inserted into database");

	}

	public void updateBook(Books book) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("bookName", book.getBookName()); // Adding a named parameter
		namedParameters.addValue("isbn", book.getISBN());
		namedParameters.addValue("price", book.getPrice());
		namedParameters.addValue("id", book.getId());
		namedParameters.addValue("description", book.getDescription());
		String query = "UPDATE book SET bookName=:bookName,ISBN = :isbn, price=:price, description=:description where id=:id  ";

		int rowsAffected = jdbc.update(query, namedParameters);

		if (rowsAffected > 0) {
			System.out.println("Insertions Successful");
		}
	}

	public List<Books> getBook() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM book";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Books>(Books.class));
	}

	public List<Books> getBookById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM book WHERE id=:id";
		namedParameters.addValue("id", id);	
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Books>(Books.class));
	}

	public void deleteBookById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "DELETE  FROM book WHERE id = :id";
		namedParameters.addValue("id", id);

		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {

			System.out.println("Deletion Successful");
		}
	}
	public void placeOrder(List<Books> cart) {
	    // Iterate through the cart and save order details to the database
	    for (Books book : cart) {
	        // Save order details to the database (you need to implement this method)
	        saveOrderDetails(book);
	    }
	}
	public void saveOrderDetails(Books book) {
	    // Implement the logic to save order details to the database
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		namedParameters.addValue("bookName", book.getBookName()); // Adding a named parameter
		namedParameters.addValue("isbn", book.getISBN());
		namedParameters.addValue("price", book.getPrice());
		namedParameters.addValue("id", book.getId());
		namedParameters.addValue("description", book.getDescription());
		namedParameters.addValue("TotalPrice", book.getQuantity()*book.getPrice());
		
		String query = "INSERT INTO orders(bookName, ISBN, price, description, TotalPrice) VALUES (:bookName,:isbn,:price,:description,:TotalPrice)";

		int rowsAffected = jdbc.update(query, namedParameters);

		if (rowsAffected > 0)
			System.out.println("Order inserted into database");

	}

}
