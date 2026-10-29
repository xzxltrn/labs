package edu.course.lab04;

final class MutableKey {

    private String name;

    MutableKey(String name) {
        this.name = name;
    }

    void setName(String name) {
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

        MutableKey other = (MutableKey) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}