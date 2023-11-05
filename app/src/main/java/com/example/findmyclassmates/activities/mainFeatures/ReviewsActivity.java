package com.example.findmyclassmates.activities.mainFeatures;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findmyclassmates.R;
import com.example.findmyclassmates.models.Course;
import com.example.findmyclassmates.models.Review;
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

        reviewAdapter = new ReviewAdapter(this, R.layout.review_item);
        reviewsListView.setAdapter(reviewAdapter);

        Query query = databaseReference.orderByChild("dept").equalTo(dept);
            query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //reviewAdapter = new ReviewAdapter(context, R.layout.review_item);
                        //reviewsListView.setAdapter(reviewAdapter);

                        for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                            Review review = reviewSnapshot.getValue(Review.class);
                            if (review.getCourseID().equals(courseID)) {

                                System.out.println("found matching reviews" + review.getOne());
                                // Retrieve review data
                                /*String dept = reviewSnapshot.child("dept").getValue(String.class);
                                String courseID = reviewSnapshot.child("courseID").getValue(String.class);
                                String one = reviewSnapshot.child("one").getValue(String.class);
                                int two = reviewSnapshot.child("two").getValue(Integer.class);
                                String three = reviewSnapshot.child("three").getValue(String.class);
                                String four = reviewSnapshot.child("four").getValue(String.class);
                                String five = reviewSnapshot.child("five").getValue(String.class);
                                int upvotes = reviewSnapshot.child("upvotes").getValue(Integer.class);
                                int downvotes = reviewSnapshot.child("downvotes").getValue(Integer.class);
                                String user = reviewSnapshot.child("user").getValue(String.class);*/

                                // Create a Review object
                                //Review review = new Review(dept, courseID, one, two, three, four, five, upvotes, downvotes, user);
                                reviewAdapter.add(review);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle database error
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
                @Override
                public void onClick(View v) {
                    int upvotes = review.getUpvotes() + 1;
                    review.setUpvotes(upvotes);
                    upvoteCountTextView.setText(String.valueOf(upvotes));
                }
            });

            downvoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int downvotes = review.getDownvotes() + 1;
                    review.setDownvotes(downvotes);
                    downvoteCountTextView.setText(String.valueOf(downvotes));
                }
            });

            return convertView;
        }
    }
}