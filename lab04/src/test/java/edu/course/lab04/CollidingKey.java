package edu.course.lab04;

final class CollidingKey {

    private final String name;

    CollidingKey(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CollidingKey other = (CollidingKey) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return 42;
    }
}