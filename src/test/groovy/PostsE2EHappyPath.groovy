import groovyx.net.http.RESTClient
import spock.lang.Specification

import static groovyx.net.http.ContentType.JSON

class PostsE2EHappyPath extends Specification {

    def client = new RESTClient('https://jsonplaceholder.typicode.com/')

    def 'user can create a new post' () {
        when:
        def response = client.post(path: 'posts',
                contentType: JSON,
                body: [userId: '1',
                       titles: 'title1',
                       body  : 'body1'])
        then:
        assert response.status == 201: 'The new post was not created.'
    }

    def 'get post' () {
        when:
//        def response = client.get(path: 'posts/1')
        def response = getData()

        then:
        assert response.status == 200 : 'Post was not retrieved.'
    }

    def 'edit the title and body of the post'() {
        when:
        def response = client.put(path: 'posts/1',
                contentType: JSON,
                body: [userId: '1',
                       titles: 'titleChanged',
                       body  : 'bodyChanged'])
        then:
        assert response.status == 200 : 'The new post was not updated.'

    }

    def 'get the post again' () {
        when:
        def response = client.post(path: 'posts/1/comments',
                contentType: JSON,
                body: [userId: '1',
                       titles: 'title1',
                       body  : 'body1'])

        then:
        assert response.status == 200 : 'Post was not retrieved.'
    }

    def 'delete the post'() {
        when:
        def response = client.delete(path: 'posts/1')

        then:
        assert response.status == 204 : 'Post was not deleted.'
    }

    def 'validate post is gone' () {
        when:
        def response = getData()

        then:
        assert response.status == 404 : 'Deleted post was invalidly retrieved'
    }

    def getData() {
        def response = client.get(path: 'posts/1')
        return response
    }

}