package ExceptionProblems;

/**
 * 1. Checked Exception: Represents an external, recoverable condition.
 * In a real backend, this might trigger a specific HTTP 403 or 503 status.
 */
class RestaurantClosedException extends Exception {
    public RestaurantClosedException(String message) {
        // Pass the error message to the parent Exception class
        super(message);
    }
}

/**
 * 2. Unchecked Exception (Runtime): Represents a logic error or validation failure.
 * Usually indicates the client sent invalid data (similar to HTTP 400 Bad Request).
 */
class MinimumOrderException extends RuntimeException {
    public MinimumOrderException(String message) {
        // Pass the error message to RuntimeException
        super(message);
    }
}

// 3. Service Layer: Contains the business logic
class OrderService {

    /**
     * Processes a food order based on business rules.
     * @param isOpen The current status of the restaurant.
     * @param total The monetary value of the order.
     * @throws RestaurantClosedException declared because it is a Checked Exception.
     */
    public void processOrder(boolean isOpen, double total) throws RestaurantClosedException, MinimumOrderException {
        // Rule 1: Restaurant must be open
        if (!isOpen) {
            // 'throw' terminates the method execution and passes the object up the stack
            throw new RestaurantClosedException("Operation Failed: The restaurant is currently closed.");
        }

        // Rule 2: Minimum order value must be met
        if (total < 100) {
            // RuntimeExceptions do not need to be declared in the 'throws' clause
            throw new MinimumOrderException("Validation Failed: Order total is below the 100 EGP limit.");
        }

        // This line only executes if no exceptions were thrown above
        System.out.println("[LOG] Order successfully validated and sent to kitchen.");
    }
}

// 4. Entry Point: Handling the exceptions
public class ResturantProblem {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        // SCENARIO: Handling a failing order due to business rules
        try {
            System.out.println("--- Attempting Transaction ---");

            // Triggering a failure: Restaurant is open, but amount is too low
            orderService.processOrder(false, 45.0);

        } catch (RestaurantClosedException e) {
            // Handle specific checked exception (e.g., notify user to try another branch)
            System.err.println("RECOVERY: " + e.getMessage());

        } catch (MinimumOrderException e) {
            // Handle validation error (e.g., ask user to add more items to cart)
            System.err.println("CLIENT ERROR: " + e.getMessage());

        } catch (Exception e) {
            // Generic catch-all for any other unexpected errors
            System.err.println("FATAL: An unknown error occurred.");

        } finally {
            // This block always runs, regardless of whether an exception occurred
            // Critical for closing database connections or clearing security contexts
            System.out.println("--- Transaction Session Closed ---");
        }
    }
}