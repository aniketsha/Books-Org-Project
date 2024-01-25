package BookStoreAssignmentAniketSharma.assignment3.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Books {
	private Long id;
	private String bookName;
	private String ISBN;
	private Long price;
	private String description;
	private int quantity;
}
