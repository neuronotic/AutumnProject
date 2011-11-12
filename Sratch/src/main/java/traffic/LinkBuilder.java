package traffic;

public interface LinkBuilder {

	Link make();

	LinkBuilder withInJunction(Junction inJunction);

	LinkBuilder withOutJunction(Junction outJunction);

	LinkBuilder withLength(int linkLength);

	LinkBuilder withName(String linkName);

}
