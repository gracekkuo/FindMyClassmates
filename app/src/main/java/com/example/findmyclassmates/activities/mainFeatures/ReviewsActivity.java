package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReviewsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String dept;
    private String courseID;

    private TextView courseTitleTextView;
    private ListView reviewsListView;
    private ReviewAdapter reviewAdapter;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent intent = getIntent();
        dept = intent.getStringExtra("dept");
        courseID = intent.getStringExtra("courseID");

        this.databaseReference = FirebaseDatabase.getInstance().getReference("reviews");

        courseTitleTextView = findViewById(R.id.courseTitleTextView);
        reviewsListView = findViewById(R.id.reviewsListView);

        courseTitleTextView.setText("Reviews for " + dept + " - " + courseID);

        context=this;


        Query query = databaseReference.orderByChild("dept").equalTo(dept);
            query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        reviewAdapter = new ReviewAdapter(context, R.layout.review_item);
                        reviewsListView.setAdapter(reviewAdapter);

                        for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                            Review review = reviewSnapshot.getValue(Review.class);
                            if (review.getCourseID().equals(courseID)) {
                                review.setUid(reviewSnapshot.getKey());
                                System.out.println("found matching reviews" + review.getOne());
                                reviewAdapter.add(review);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
                    }
                });

            Button submitReviewButton = findViewById(R.id.leaveReview);
            submitReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Create an Intent to start the new activity
                    Intent intent = new Intent(ReviewsActivity.this, LeaveReviewActivity.class);

                    // Pass the values as extras in the Intent
                    intent.putExtra("dept", dept);
                    intent.putExtra("courseID", courseID);

                    // Start the new activity
                    startActivity(intent);
                }
            });
    }

    private class ReviewAdapter extends ArrayAdapter<Review> {
        ReviewAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.review_item, parent, false);
            }

            TextView review1 = convertView.findViewById(R.id.review1);
            TextView review2 = convertView.findViewById(R.id.review2);
            TextView review3 = convertView.findViewById(R.id.review3);
            TextView review4 = convertView.findViewById(R.id.review4);
            TextView review5 = convertView.findViewById(R.id.review5);
            TextView upvoteCountTextView = convertView.findViewById(R.id.upvoteCountTextView);
            TextView downvoteCountTextView = convertView.findViewById(R.id.downvoteCountTextView);
            ImageButton upvoteButton = convertView.findViewById(R.id.upvoteButton);
            ImageButton downvoteButton = convertView.findViewById(R.id.downvoteButton);
            Button deleteButton = convertView.findViewById(R.id.deleteReviewButton);
            TextView user = convertView.findViewById(R.id.user);

            Review review = getItem(position);

            review1.setText(review.getOne());
            review2.setText(String.valueOf(review.getTwo()));
            review3.setText(review.getThree());
            review4.setText(review.getFour());
            review5.setText(review.getFive());
            upvoteCountTextView.setText(String.valueOf(review.getUpvotes()));
            downvoteCountTextView.setText(String.valueOf(review.getDownvotes()));
            user.setText("- "+review.getUser());

            upvoteButton.setOnClickListener(new View.OnClickListener() {
                //TODO: make sure that the database gets incremented
                @Override
                public void onClick(View v) {
                    DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child(review.getUid());
                    if (upvoteButton.getTag() == null) {
                        // First click - increment upvotes
                        int upvotes = review.getUpvotes() + 1;
                        review.setUpvotes(upvotes);
                        upvoteCountTextView.setText(String.valueOf(upvotes));
                        reviewRef.child("upvotes").setValue(review.getUpvotes());
                        upvoteButton.setTag("upvoted"); // Mark as upvoted
                    } else {
                        // Second click - undo upvote
                        int upvotes = review.getUpvotes() - 1;
                        review.setUpvotes(upvotes);
                        upvoteCountTextView.setText(String.valueOf(upvotes));
                        reviewRef.child("upvotes").setValue(review.getUpvotes());
                        upvoteButton.setTag(null); // Remove the upvote
                    }
                }
            });

            downvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child(review.getUid());
                    if (downvoteButton.getTag() == null) {
                        // First click - increment downvotes
                        int downvotes = review.getDownvotes() + 1;
                        review.setDownvotes(downvotes);
                        downvoteCountTextView.setText(String.valueOf(downvotes));
                        reviewRef.child("downvotes").setValue(review.getDownvotes());
                        downvoteButton.setTag("downvoted"); // Mark as downvoted
                    } else {
                        // Second click - undo downvote
                        int downvotes = review.getDownvotes() - 1;
                        review.setDownvotes(downvotes);
                        downvoteCountTextView.setText(String.valueOf(downvotes));
                        reviewRef.child("downvotes").setValue(review.getDownvotes());
                        downvoteButton.setTag(null); // Remove the downvote
                    }
                }
            });

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(currentUser.getUid() + " " + review.getUserId());
                    if (currentUser != null && currentUser.getUid().equals(review.getUserId())) {
                        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child(review.getUid());

                        reviewRef.removeValue()
                                .addOnSuccessListener(aVoid -> {
                                    // Remove the item from the adapter and notify the changes
                                    remove(review);
                                    notifyDataSetChanged();
                                })
                                .addOnFailureListener(e -> {
                                });
                    } else {
                        // The logged-in user is not the creator of this review
                        Toast.makeText(getContext(), "You don't have permission to delete this review.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return convertView;
        }
    }
}