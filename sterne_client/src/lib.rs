#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        assert_eq!(2 + 2, 4);
    }
}

pub trait SterneClient {
    pub fn Connect(&self, String addr, Option<i16> port) {
        // Create a TCP connection to the server using this information
        // How do I do this again?
    }

}
pub struct SterneSubscriber {}
pub struct SternePublisher {}

impl SterneClient for SterneSubscriber {}
impl SterneClient for SternePublisher {}

