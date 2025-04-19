// Question 2

class FrmsSystem {
    Map<String, User> users = new HashMap<>();

    // Registration Use Case
    void registerUser(String id, String name, Role role) {
        assert id != null && !id.isBlank() : "ID cannot be null or blank";
        assert name != null && !name.isBlank() : "Name cannot be null or blank";
        assert !users.containsKey(id) : "User ID already exists";

        users.put(id, new User(id, name, role == null ? Role.CUSTOMER : role));
        assert users.containsKey(id) : "User was not registered successfully";

        checkInvariant();
    }

    // Assign Manager Use Case
    void assignManager(String id) {
        assert users.containsKey(id) : "User does not exist";
        assert users.get(id).role == Role.CREW : "Only CREW can be promoted";

        users.get(id).role = Role.MANAGER;
        assert users.get(id).role == Role.MANAGER : "User was not promoted successfully";

        checkInvariant();
    }

    // Add / Remove Crew Use Case
    void addCrew(String id, String name) {
        assert id != null && !id.isBlank();
        assert name != null && !name.isBlank();
        assert !users.containsKey(id);

        registerUser(id, name, Role.CREW);
        assert users.containsKey(id) : "Crew not added properly";

        checkInvariant();
    }

    void removeCrew(String id) {
        assert users.containsKey(id) : "User does not exist";
        assert users.get(id).role == Role.CREW || users.get(id).role == Role.MANAGER : "Only CREW or MANAGER can be removed";

        users.remove(id);
        assert !users.containsKey(id) : "User not removed successfully";

        checkInvariant();
    }

    void checkInvariant() {
        long managerCount = users.values().stream().filter(u -> u.role == Role.MANAGER).count();
        long crewCount = users.values().stream().filter(u -> u.role == Role.CREW).count();
        assert managerCount <= (managerCount + crewCount) : "Too many managers!";
    }
}
